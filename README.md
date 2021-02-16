# Flash_sport

I use Spring boot framework to make this application, with Java programing language.
For data persistence I chose to use mySql.
The front end is made in Thymeleaf.
I also use a dependency for Security like Spring Security, dependency for payment system like Spring Stripe.
The application is made in order to enable customers to see the latest products in the store and to order electronically what they want.
The user can see all latest product in the product page, but if she/he want to add that product in his shopping card, she/he must be registered in the system and must be logged in.
If the user is not registered in the system, he/she is redirected to login page, and he/she has to choose the link that leads to register page.
If the user is registered to the system he add the chosen products to shopping card, and if he/she want to see them he must chose the link that leads to shopping card page, which is placed in the user profile. Also the user can also remove some of the products in shopping and bring them back to product page.
In the User profile page the user can see all the information about him.
If the user clicked the Buy button a dialogue opens up for him and he had to enter his email, credit card number, and the date on which the payment will be made. When the user finished this, to the user is sent a return message on his e-mail that the payment has been made and that the delivery will arrive soon. If the user clicked the cancel button before buy button all product will be removed from shopping card to product page, and in the shopping card page there will be an empty list of product and 0$ sum.
Also the user can publish its own product, if he wants it to be sold at a certain price.
The user can also see the statistics for the products in chart. For that functionality I used the javaScript  library highcharts.js, that is very useful when we want to show the stored data statistics In chart. I chose to show the percentage results for the costs of the products.
In this application there are 2 type of users. One user is administrator, and the others are ordinary users. If the logged user is in role ordinary user he cannot see the link that leads to page where you can see all users registered in the system. If the logged user is in role administrator he can see the link that leads to page where you can see all users registered in the system, but he cannot add a new product in product list or access the shopping card in his profile. The administrator user can see the all products in the system, but cannot manipulate with them.
