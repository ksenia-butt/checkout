A simplified simulation of a supermarket checkout

--------EXECUTION------
Run the project locally with 'mvn spring-boot:run'


--------ENDPOINTS------
GET localhost:8080/totalPrice?items=A,B,A

The endpoint calculates total price of specified items(SKUs). Currently, there are 3 products configured in the in-memory
database: A, B and C. I haven't had enough time to implement an endpoint that would add new products but new products
could be added directly into the database by modifying the data.sql file.
When calculating the total, if one of the products cannot be found in the database Bad Request response will be returned to the client.

POST localhost:8080/promotion   with payload of the following format:
    {
      "sku": "C",
      "quantity": 5,
      "pricePerQuantity": 35
    }

The endpoint saves the new promotion in the database. If there is no product with the specified sku in the database,
Bad Request response will be returned to the client.

--------ASSUMPTIONS------
1. SKUs are collated by client into a list which is then passed to the checkout service to calculate the total price
for all items in the list.
2. SKUs are aggregated in the checkout service.
3. Total price is calculated only if all items exist, otherwise Bad Request is returned.
4. Only Multi-Buy offers are considered at this point without any catering for other type of promotions.
5. Only one multi-offer per product.
6. Items that fall outside the promotion quantity are calculated at normal price. For example, if product is priced at £5
with a promotion 3 for £10, then for 7 items the price will be worked out as: 3 for £10 twice + 1 iem for £5 = £25
7. API Documentation tool (e.g. Swagger) was considered out of scope

------MAJOR TODOs---------
1. Introduce expiry date to promotion. Promotion can only be applied if it exists AND not expired.
2. Implement addPromotions endpoint that would take a collection of promotions. There'd need to be some validation that
checks that collection doesn't have multiple promotions for the same SKU.
3. JPA tests


