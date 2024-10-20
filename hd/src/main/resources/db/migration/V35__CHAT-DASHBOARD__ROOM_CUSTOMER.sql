CREATE OR REPLACE FUNCTION chat.func_room_customer_quantity_plpgsql(p_initial_date date, p_final_date date)
    RETURNS TABLE(username varchar(20), quantity bigint) 
    LANGUAGE 'plpgsql'
AS $$
BEGIN
	RETURN QUERY 
		SELECT 
				AUTU.USERNAME
			 ,  COUNT(CHTR.ID)
		  FROM  
		  		CHAT.TB_ROOM CHTR
    inner JOIN 
	 			AUTH.TB_USER AUTU ON CHTR.FK_USER_CUSTOMER = AUTU.ID 
		   AND 
		   		CHTR.ROOM_STATUS != 0
		   AND 
		   		CHTR.CREATE_DATETIME >= p_initial_date 
		   AND 
		   		CHTR.CREATE_DATETIME <= p_final_date
      GROUP BY 
	  			AUTU.USERNAME
	  ORDER BY 
				2 DESC
		 LIMIT
				5;
END
$$;
