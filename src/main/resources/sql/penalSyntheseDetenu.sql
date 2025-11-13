--DEFINE tnumide = '0100193153'
--DEFINE tcoddet = '003'

WITH affaire_data AS (
    SELECT 
        TAFF.tnumseqaff,
        TR.nature_tribunal,
        TR.libelle_tribunal,
        TAFF.tnumjaf,
        TAFF.typema,
        NA.libelle_nature,
        NA.type_affaire,

        -- 🔹 Accusations concaténées
        CASE 
            WHEN TAFF.typema = '3' THEN (
                SELECT LISTAGG(f.libelle_famille_acc, ' و ')
                WITHIN GROUP (ORDER BY t.tcodacc)
                FROM taccusation@LINK_PENALE t
                JOIN famille_accusation@LINK_PENALE f 
                    ON t.tcodfac = f.code_famille_acc
                WHERE t.tnumide = TAFF.tnumide 
                  AND t.tcoddet = TAFF.tcoddet 
                  AND t.tcodextj = TAFF.tntypema
            )
            WHEN TAFF.typema IN ('1', 'T') THEN (
                SELECT t.ttextma 
                FROM TMANDATDEPOT@LINK_PENALE t
                WHERE t.tnumide = TAFF.tnumide 
                  AND t.tcoddet = TAFF.tcoddet 
                  AND t.tcodma = TAFF.tntypema
            )
            WHEN TAFF.typema = '4' THEN (
                SELECT f.libelle_famille_acc 
                FROM tcontrainte@LINK_PENALE t
                JOIN famille_accusation@LINK_PENALE f 
                    ON t.tcodfac = f.code_famille_acc
                WHERE t.tnumide = TAFF.tnumide 
                  AND t.tcoddet = TAFF.tcoddet 
                  AND t.tcodcon = TAFF.tntypema
            )
            ELSE NULL 
        END AS accusations_concatenees,

        -- 🔹 Durées cumulées
        CASE WHEN TAFF.typema = '3' THEN (
            SELECT SUM(t.tduracca)
            FROM taccusation@LINK_PENALE t
            WHERE t.tnumide = TAFF.tnumide 
              AND t.tcoddet = TAFF.tcoddet 
              AND t.tcodextj = TAFF.tntypema
        ) ELSE 0 END AS total_annees_raw,

        CASE WHEN TAFF.typema = '3' THEN (
            SELECT SUM(t.tduraccm)
            FROM taccusation@LINK_PENALE t
            WHERE t.tnumide = TAFF.tnumide 
              AND t.tcoddet = TAFF.tcoddet 
              AND t.tcodextj = TAFF.tntypema
        ) ELSE 0 END AS total_mois_raw,

        CASE WHEN TAFF.typema = '3' THEN (
            SELECT SUM(t.tduraccj)
            FROM taccusation@LINK_PENALE t
            WHERE t.tnumide = TAFF.tnumide 
              AND t.tcoddet = TAFF.tcoddet 
              AND t.tcodextj = TAFF.tntypema
        ) ELSE 0 END AS total_jours_raw,

        -- 🔹 Type et nature du jugement
        CASE WHEN TAFF.typema = '3' THEN (
            SELECT tj.TYPE_TJUGEMENT 
            FROM typejugement@LINK_PENALE tj 
            WHERE tj.CODE_TJUGEMENT = (
                SELECT t.tcodtju
                FROM tjugement@LINK_PENALE t
                WHERE t.tnumide = TAFF.tnumide 
                  AND t.tcoddet = TAFF.tcoddet 
                  AND t.tcodextj = TAFF.tntypema
            )
        ) ELSE NULL END AS type_jugement,

        CASE WHEN TAFF.typema = '3' THEN (
            SELECT tj.nature_jugement 
            FROM typejugement@LINK_PENALE tj 
            WHERE tj.CODE_TJUGEMENT = (
                SELECT t.tcodtju
                FROM tjugement@LINK_PENALE t
                WHERE t.tnumide = TAFF.tnumide 
                  AND t.tcoddet = TAFF.tcoddet 
                  AND t.tcodextj = TAFF.tntypema
            )
        ) ELSE NULL END AS nature_jugement,

        TAFF.tntypema,
        TAFF.tdatdep,
        TAFF.TCODSIT,

        ROW_NUMBER() OVER (
            PARTITION BY TAFF.tnumseqaff  
            ORDER BY  
                TAFF.tdatdep DESC,
                CASE TAFF.TCODSIT  
                    WHEN 'O' THEN 1 
                    WHEN 'L' THEN 2 
                    WHEN 'ت' THEN 3 
                    WHEN 'ن' THEN 4 
                    WHEN 'ط' THEN 5 
                    WHEN 'F' THEN 6 
                    ELSE 7 
                END
        ) AS rn

    FROM TIDEAFF@LINK_PENALE TAFF
    JOIN tribunal@LINK_PENALE TR 
        ON TAFF.tcodtri = TR.code_tribunal
    JOIN natureAffaire@LINK_PENALE NA 
        ON TAFF.tcodtaf = NA.code_nature
   -- WHERE TAFF.tnumide = &tnumide 
    --  AND TAFF.tcoddet = &tcoddet
    WHERE TAFF.tnumide = :tnumide AND TAFF.tcoddet = :tcoddet
),

affaire_filtree AS (
    SELECT *
    FROM affaire_data
    WHERE rn = 1
),

affaire_fin AS (
    SELECT 
        tnumseqaff,
        libelle_nature,
        libelle_tribunal,
        tnumjaf,
        accusations_concatenees,
        TO_CHAR(tdatdep, 'YYYY-MM-DD') AS tdatdep,
        TRUNC(SUM(total_annees_raw) + FLOOR((SUM(total_mois_raw) + FLOOR(SUM(total_jours_raw)/30)) / 12)) AS total_annees,
        MOD(FLOOR(SUM(total_mois_raw) + FLOOR(SUM(total_jours_raw)/30)), 12) AS total_mois,
        MOD(SUM(total_jours_raw), 30) AS total_jours,
        typema AS type_document,
        TCODSIT,
        type_jugement,
        nature_jugement,
        nature_tribunal,
        type_affaire,

        ROW_NUMBER() OVER (
            ORDER BY 
                CASE WHEN TCODSIT = 'L' THEN 0 WHEN TCODSIT = 'F' THEN 1 ELSE 2 END DESC,
                CASE 
                    WHEN typema = '1' THEN 1
                    WHEN typema = 'T' THEN 2
                    WHEN typema = '3' AND type_jugement = '0' THEN 3
                    WHEN typema = '3' AND type_jugement != '0' THEN 4
                    WHEN typema = '2' THEN 5
                    WHEN typema = '4' THEN 6
                    ELSE 6
                END ASC,
                CASE WHEN type_jugement = '0' THEN 999999 ELSE TO_NUMBER(type_jugement) END,
                CASE 
                    WHEN nature_jugement = '1' THEN 1
                    WHEN nature_jugement = '2' THEN 2
                    WHEN nature_jugement = '3' THEN 3
                    WHEN nature_jugement = '4' THEN 4
                    ELSE -1
                END DESC,
                CASE 
                    WHEN TO_NUMBER(type_affaire) = 0 THEN 99999
                    ELSE TO_NUMBER(type_affaire)
                END ASC,
                CASE 
                    WHEN nature_tribunal IN ('00','0') THEN 999999
                    ELSE TO_NUMBER(nature_tribunal)
                END,
                TRUNC(SUM(total_annees_raw) + FLOOR((SUM(total_mois_raw) + FLOOR(SUM(total_jours_raw)/30)) / 12)) DESC,
                MOD(FLOOR(SUM(total_mois_raw) + FLOOR(SUM(total_jours_raw)/30)), 12) DESC,
                MOD(SUM(total_jours_raw), 30) DESC,
                tdatdep
        ) AS row_affaire_principale 

    FROM affaire_filtree
    GROUP BY
        tnumseqaff,
        tntypema,
        libelle_nature,
        libelle_tribunal,
        tnumjaf,
        accusations_concatenees,
        tdatdep,
        typema,
        TCODSIT,
        type_jugement,
        nature_jugement,
        nature_tribunal,
        type_affaire
)


SELECT       
    af.tnumseqaff,  
    af.libelle_tribunal,                          
    SUBSTR(af.tnumjaf, 4, 6) || ' - ' || SUBSTR(af.tnumjaf, 1, 3) AS tnumjaf_formatte,                        
    af.accusations_concatenees,                  
    af.tdatdep,     
    af.libelle_nature,
    NULL AS totale
FROM affaire_fin af     
 
 
WHERE row_affaire_principale = 1
  AND ROWNUM = 1 -- car oracle ne suport pas  FETCH FIRST 1 ROWS ONLY
