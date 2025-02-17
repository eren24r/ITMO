import api

token = "2D40D1D6-36AB-47E4-92B8-72BDAC0AC323"
urlDate = "https://pms.frontdesk24.ru/externalPrices/jsAvailables.aspx"
urlPrice = "https://pms.frontdesk24.ru/externalPrices/jsSearchJoin.aspx"

paramDate = {"token" : token,
		"dateFrom" : "2023-08-11",
		"dateTo" : "2023-09-11",
		"plainJson" : "1"}

paramPrice = {"arrival" : "2024-12-20%2010:00",
		"depart" : "2024-12-20%2020:00",
		"adults" : "0",
		"filter" : [{"token" : token}],
		"plainJson" : "1"}


print(api.getFunc(urlPrice, paramPrice))