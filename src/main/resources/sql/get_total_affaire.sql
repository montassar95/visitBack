WITH Affaires AS (
    SELECT 
        TAFF.tnumseqaff,
        ROW_NUMBER() OVER (
            PARTITION BY TAFF.tnumseqaff  
            ORDER BY  
                TAFF.tdatdep DESC 
                 
        ) AS rn
    FROM TIDEAFF@LINK_PENALE TAFF
    WHERE TAFF.tnumide = :tnumide AND TAFF.tcoddet = :tcoddet
)
SELECT COUNT(*) 
FROM Affaires 
WHERE rn = 1 