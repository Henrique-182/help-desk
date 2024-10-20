CREATE OR REPLACE FUNCTION chat.func_sector_month_quantity_plpgsql(p_year integer)
    RETURNS TABLE(sector_description character varying, jan bigint, feb bigint, mar bigint, apr bigint, may bigint, jun bigint, jul bigint, aug bigint, sep bigint, oct bigint, nov bigint, dece bigint) 
    LANGUAGE 'plpgsql'
AS $$
BEGIN
	RETURN QUERY 
		SELECT 
        		CHTS.DESCRIPTION,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 1 THEN 1 ELSE 0 END) AS JAN,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 2 THEN 1 ELSE 0 END) AS FEB,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 3 THEN 1 ELSE 0 END) AS MAR,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 4 THEN 1 ELSE 0 END) AS APR,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 5 THEN 1 ELSE 0 END) AS MAY,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 6 THEN 1 ELSE 0 END) AS JUN,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 7 THEN 1 ELSE 0 END) AS JUL,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 8 THEN 1 ELSE 0 END) AS AUG,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 9 THEN 1 ELSE 0 END) AS SEP,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 10 THEN 1 ELSE 0 END) AS OCT,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 11 THEN 1 ELSE 0 END) AS NOV,
				SUM(CASE WHEN EXTRACT(MONTH FROM CHTR.create_datetime) = 12 THEN 1 ELSE 0 END) AS DECE
          FROM 
		  		CHAT.TB_SECTOR CHTS
     LEFT JOIN 
	 			CHAT.TB_ROOM CHTR ON CHTR.FK_SECTOR = CHTS.ID
         WHERE 
		 		EXTRACT(YEAR FROM CHTR.create_datetime) = P_YEAR
      GROUP BY 
	  			CHTS.DESCRIPTION;
END
$$;