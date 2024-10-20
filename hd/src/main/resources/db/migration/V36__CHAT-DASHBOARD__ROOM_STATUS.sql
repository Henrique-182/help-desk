CREATE OR REPLACE FUNCTION chat.func_room_status_quantity_plpgsql(p_initial_date date, p_final_date date)
    RETURNS TABLE(status int, quantity bigint) 
    LANGUAGE 'plpgsql'
AS $$
BEGIN
	RETURN QUERY 
		  WITH 
				TEMP_ROOM_STATUS 
		  AS (
				SELECT 0 AS room_status
				UNION ALL
				SELECT 1
				UNION ALL
				SELECT 2
				UNION ALL
				SELECT 3
				UNION ALL
				SELECT 4
			 )
		SELECT 
				TERS.room_status, 
				COUNT(CHTR.room_status)
		  FROM 
				TEMP_ROOM_STATUS TERS
	 LEFT JOIN 
				CHAT.TB_ROOM CHTR ON TERS.room_status = CHTR.room_status
		   AND 
		   		CHTR.create_datetime >= p_initial_date
		   AND 
		   		CHTR.create_datetime <= p_final_date
	  GROUP BY 
				TERS.room_status
	  ORDER BY 
				TERS.room_status;
END
$$;
