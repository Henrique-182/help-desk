INSERT INTO AUTH.TB_USER (username, fullname, password, fk_user_type, fk_department) VALUES
	('ADM', 'ADM', '8b70c6410b1a1a8bc92773ee2d8a50d8a5a14f959b5df29492d2af2ac7e6eb8dc7939c6a4599a2d5', 1, null),
	('MANAGER', 'MANAGER', '24487108991705abd20ef0a5fa2c7ca4ab671dcc8974d8eae6955842bbd2844a6746cc42c38212cc', 2, 1),
	('COMMON_USER', 'COMMON_USER', '6be1d1af901d4c688f752289985c89f4ac2cce0a4ddf7eac6216640246e6fecdd1c77e66d450f468', 3, null),
	('func1', 'func1', 'fcb89403be394a744005fc8b45d182c71825aa469e2f9f5eb5420e127eb89a23d45fda13450d9505', 2, 2),
	('func2', 'func2', '7b89e7b923c46f14f26d037fe9a660622fb4de10a92873c75a6374afa015fb41697c9d453bdedc2d', 2, 3),
	('func3', 'func3', 'd99f688e1f0369750b9f73f8cce5c2921c951c6b7b564cc4cedd9720ad50c80a84f042d9993ca437', 2, 4),
	('func4', 'func4', 'c6bd987fcc3a7d4f85db93ebe34a59288fdfd0de6bef0c876ad9128aca71646831384100126d8f61', 2, 5),
	('clie1', 'clie1', 'a8b68ef3faa01e95a42a2bf2fb024e211a1f406ed4bd64eb575e0cdd7cbdfe4a7e61ef8c6b0c9be8', 3, null),
	('clie2', 'clie2', '173edbcf53cbbcb175d30dcf1460b4802d1b20f5939a18d9e312e29836ebd4ec71f849dfb1816dcf', 3, null),
	('clie3', 'clie3', '927339c30e9d20008901cf1c68942132eb27a13f19becadaf646c7e1ee1f4219cee06355b0cbc133', 3, null);
