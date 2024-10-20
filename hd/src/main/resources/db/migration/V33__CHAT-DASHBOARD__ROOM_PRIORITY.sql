CREATE OR REPLACE FUNCTION chat.func_room_priority_quantity_plpgsql(p_initial_date date, p_final_date date)
    RETURNS TABLE(priority varchar(20), quantity bigint) 
    LANGUAGE 'plpgsql'
AS $$
BEGIN
	RETURN QUERY 
		SELECT 
				CTRP.description
			 ,  COUNT(CHTR.FK_ROOM_PRIORITY) 
		  FROM  
		  		CHAT.TB_ROOM_PRIORITY CTRP
     LEFT JOIN 
	 			CHAT.TB_ROOM CHTR ON CHTR.FK_ROOM_PRIORITY = CTRP.ID 
		   AND 
		   		CHTR.CREATE_DATETIME >= p_initial_date 
		   AND 
		   		CHTR.CREATE_DATETIME <= p_final_date
      GROUP BY 
	  			CTRP.DESCRIPTION, CTRP.ID 
	  ORDER BY 
	  			CTRP.ID;
END
$$;