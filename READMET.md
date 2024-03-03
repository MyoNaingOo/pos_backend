<h4>Docker image</h4>
<p>you can get docker image<a href="#docker"> More details</a></p><br>

<ul>
    User
    <li>
        <a href="#user_reg" >User Register</a>
    </li>
    <li>
        <a href="#user_login" >User Login</a>
    </li>
    <li>
        <a href="#otp" >Login and Register Otp</a>
    </li>
    <li>
        <a href="#shop" >Shop and user shop</a>
    </li>
    <li>
        <a href="#other_user_api" >Other Api for User</a>
    </li>
    <li>
        <a href="#custom_page" >Customize pagination </a>
    </li>

</ul>

<ul>
    Other
    <li>
        <a href="#img_crud" >Image CRUD</a>
    </li>
    <li>
        <a href="#product_obj" >Product and Price CRUD</a>
    </li>
    <li>
        <a href="#store_obj" >Store CRUD</a>
    </li>
    <li>
        <a href="#sale_obj" >Sale CRUD</a>
    </li>
</ul>


<h3 id="user_reg">User Register</h3>

Register :  http://localhost:8080/api/auth/register <br>
you can get admin role <a href="#admin_role"> click here</a><br>
Register request obj :

```json
{
  "name": "",
  "gmail": "",
  "password": ""
}
```

register response data <br>
true is already have been. false is available

```json
{
  "id": 1,
  "name": "**",
  "user_img": null,
  "gmail": "***@gmail",
  "address": "**",
  "role": "ADMIN",
  "nameNotAvailable" : true,
  "gmailNotAvailable" : false
}
```



Reg Otp  : http://localhost:8080/api/otp/register<br>


```json
{
  "gmail": "**@gmail",
  "otp": 123456
}
```



<h3 id="user_login">User Login and Forget Password</h3>
Login    : http://localhost:8080/api/auth/authenticate


```json
{
  "gmail": "",
  "password": ""
}
```

forget password: http://localhost:8080/api/auth/forGetPass
```json
{
  "gmail": "**@gmail"
}
```


Login Otp: http://localhost:8080/api/otp/authenticate
<br>Login request obj :

```json
{
  "gmail": "**@gmail",
  "otp": 123456
}
```


<h3 id="otp">Login OR Register OR ForgetPassword Otp Response</h3>
Otp response obj

```json
{
  "email": "***@gmail",
  "logined": true,
  "checkotp": true,
  "massage": "Successful Login",
  "token": "*****",
  "user": {
    "id": 1,
    "name": "**",
    "user_img": null,
    "password": "***",
    "gmail": "***@gmail",
    "shop": {
      ...
    },
    "role": "ADMIN",
    "enabled": true,
    "accountNonLocked": true,
    "username": "**@gmail",
    "authorities": [
      {
        "authority": "ADMIN"
      }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true
  }
}
```

I am using json web token(JWT) authentication.So please store
token value.And then other request (Logined request) doing Bearer token on header

Example in Angular

```
public updateuser(user: any) {
    const tokenStr = 'Bearer ' + sessionStorage.getItem('token');
    const headers= new HttpHeaders().set("Authorization",tokenStr);
    return this.http.post<UserModule>(this.api+"user/update",user,{headers});
}
```

More details example in angular : https://github.com/MyoNaingOo/shopAng/blob/master/src/app/service/api.service.ts
<br>

<h3 id="shop">Shop and User Shop</h3>
Add shop information  of user  : http://localhost:8080/api/v2/user/change/shop?new_user=true (new user true for now register user)<br>

```json
{
 "shop": {
  "id": **
 }
}

```

Shop APIs <br>
Shops  : http://localhost:8080/api/v2/shop/add<br>
Shops  : http://localhost:8080/api/v2/shop/shops<br>
get Shop  : http://localhost:8080/api/v2/shop/${id} (id = shop id )<br>
update  : http://localhost:8080/api/v2/shop/update/${id} (id = shop id )<br>
delete  : http://localhost:8080/api/v2/shop/delete/${id} (id = shop id )<br>
response one shop obj:
```json
 {
  "name": "",
  "region": "",
  "town": ""
 }
```

<h3 id="custom_page">Customize pagination</h3> <br>
pagination number start is zero (0) <br>
 http://localhost:8080/api/***/***/{num}?pageSize=${30}&desc=${true} (desc: boolean = descending {true or false}  ) <br>

 http://localhost:8080/api/***/***/page <br>
response :
```json
{
  "number" : 20,  //result number
  "page_number": 1  // pagination action number 
}
```


<h3 id="other_user_api">Other Api for User</h3>
<p style="color:#f80" >It has Logined status</p>

for worker users
Get User info    : http://localhost:8080/api/v2/user/user (Logined user information)
Get User info include shop   : http://localhost:8080/api/v2/user/userInfo (Logined user information)
Get User info By id   : http://localhost:8080/api/v2/user/userid/${id} <br>
Get User info By name   : http://localhost:8080/api/v2/user/username/${name} <br>
Get User info By gmail   : http://localhost:8080/api/v2/user/usergmaill/${gmail} <br>
Get Users info : http://localhost:8080/api/v2/user/shop/page/${num} <br>

Logout Acc : http://localhost:8080/api/auth/logout GET Method<br>
Delete My Acc : http://localhost:8080/api/v1/user/delete <br>

response obj in one user information :

```json
{
 "id": 1,
 "name": "myo",
 "user_img": null,
 "password": null,
 "gmail": "myo@gmail",
 "shop": {
  "name": "",
  "region": "",
  "town": ""
 },
 "role": "USER",
 "enabled": true,
 "username": "myo@gmail",
 "authorities": [
  {
   "authority": "USER"
  }
 ],
 "accountNonExpired": true,
 "accountNonLocked": true,
 "credentialsNonExpired": true
}
```

<p style="color:#ff5" >
Deleted user account doing. other this account has been sale,store and product date is null.
</p>
<br>
Using user obj request for this

```json
{
  "id": 1,
  //id can't change
  "name": "**",
  "user_img": "img",
  "password": null,
  "gmail": "***@gmail",
  //gmail can't change
  "shop": {
    ...
  },
  "role": "USER"
}
```

Image update: http://localhost:8080/api/v1/user/image
PUT request :

```json
{
  "user_img": "***"
  // file name 
}
```

Name update: http://localhost:8080/api/v1/user/changeName
PUT request :

```json
{
  "name": "***"
}
```

password update: http://localhost:8080/api/v1/user/changePass
PUT request :

```json
{
  "password": "***"
}
```

Address update: http://localhost:8080/api/v1/user/change/shop
PUT request :

```json
{
  "address": "***"
}
```

User Manger control panel
Get User info    : http://localhost:8080/api/v2/user/user (Logined user information)
Get User info include shop   : http://localhost:8080/api/v2/user/userInfo (Logined user information)
Get User info By id   : http://localhost:8080/api/v2/user/userid/${id} <br>
Get User info By name   : http://localhost:8080/api/v2/user/username/${name} <br>
Get User info By gmail   : http://localhost:8080/api/v2/user/usergmaill/${gmail} <br>
Get Users info : http://localhost:8080/api/v2/user/shop/page/${num} <br>



<h4 id="img_crud" >Image CRUD</h4>
Image Add : http://localhost:8080/api/image/add POST Method <br>
Get Image : http://localhost:8080/api/image/${filename} GET Method <br>
Delete Image : http://localhost:8080/api/image/delete/${filename} DELETE Method <br>
but delete image is auto delete.Product or User account doing delete with image auto delete

<h4 id="product_obj" >Product obj:
</h4>

```json
{
  "id": 1,
  "name": "",
  "img": "",
  "user_id": "",
  "user": "",
  "description": "",
  "balance": 300,
  "price": {
    "product_id": 1,
    "org_price": "",
    "promo_price": "",
    "date": ""
  },
  "time": ""
}

```

Product price obj

```json
{
  "product_id": 1,
  "org_price": "",
  "promo_price": "",
  "date": ""
}
```

Product add     :  http://localhost:8080/api/v1/product/add
request obj

```json
{
  "name": "",
  "img": "",
  "description": ""
}

```

Price add     :  http://localhost:8080/api/v1/price/add
request obj

```json
{
  "product_id": 1,
  "org_price": "",
  "promo_price": ""
}
```


Product delete  :  http://localhost:8080/api/v1/product/delete/${id} id = product_id DELETE/Method<br>
Price delete  :  http://localhost:8080/api/v1/price/delete/${id} id = product_id DELETE/Method<br>
but Product has been sale,store,price data added not available delete <br>

product_details : http://localhost:8080/api/v1/product/${id} id = product_id <br>
response <a href="#product_obj" >Product Obj</a> <br>

products : http://localhost:8080/api/v1/product/products <br>
products find by month : http://localhost:8080/api/v1/product/findByMonth/${num} <br>
products with page : http://localhost:8080/api/v1/product/page/${num} <br>

response array data of product <br>

<h4 id="store_obj" >Store obj:</h4>

```json
{
  "id": "",
  "product": "",
  "user": "",
  "bulk": "",
  "time": "",
  "update_bulk": ""
}
```

Store add : http://localhost:8080/api/v1/store/add <br>
request obj:

```json
{
  "product_id": 1,
  "bulk": 300
}
```

Stores List: http://localhost:8080/api/v1/store/stores <br>

Stores All List with page: http://localhost:8080/api/v1/store/page/${num} <br>

Stores products balance : http://localhost:8080/api/v1/store/prosBalance/${num} <br>
Stores products Sold out : http://localhost:8080/api/v1/store/prosBalance/${num} <br>
Stores findAll By
Monthly : http://localhost:8080/api/v1/store/findAllByMonth/${num}?month=${month_num}&&year=${year_num} <br>
findAll Stores By Product : http://localhost:8080/api/v1/store/findAllByProduct/${num} <br>
Stores By Product : http://localhost:8080/api/v1/store/findAllByProduct/${num} <br>
Store delete : http://localhost:8080/api/v1/store/delete/${id} id=product_id <br>

<h4 id="sale_obj" >
Sale obj
</h4>

```json
{
  "user": {
    "id": 1,
    "name": "**",
    "user_img": null,
    "password": null,
    "gmail": "***@gmail",
    "address": "**",
    "role": "USER"
  },
  "saleProList": [
    {
      "product_id": 1,
      "bulk": 200
    },
    {
      "product_id": 2,
      "bulk": 200
    }
  ],
  "time": ""
}


```

Sale add : http://localhost:8080/api/v1/sale/add <br>
request obj:

```json
{
  "saleProList": [
    {
      "product_id": 1,
      "bulk": 200
    },
    {
      "product_id": 2,
      "bulk": 200
    }
  ]
}

```

Sale List : http://localhost:8080/api/v1/sale/sales <br>
Sale with page : http://localhost:8080/api/v1/sale/page/${num} <br>
Sale findByMonth : http://localhost:8080/api/v1/sale/findByMonth/${num}?month=${month_num}&&year=${year_num} <br>
Sale delete : http://localhost:8080/api/v1/sale/delete/${num} <br>



<h4 id="docker" >Docker image</h4>

step 1

check tag version <a href="https://hub.docker.com/r/myonaingoo/posbackend/tags" >More details</a>

```text
    docker pull myonaingoo/posbackend:1.7
``` 
step 2<br>
mysql image need to store
```text
    docker pull mysql
```
some config doing start<br>
step 3<br>
start run mysql server <br>
```text
    docker network create posnet
    docker run --name mysql_container --network posnet -p 3307:3306 -e MYSQL_ROOT_PASSWORD=yourpassword -d mysql 
```
step 4<br>
create database<br>
note create database name is business
```
    docker exec -it mysql_container bash 
    mysql -u root -p
    Enter Password:yourpassword 
    create database business
    show databases; // show all database
    exit
    
```
step 5 <br>
run backend image<br>
MYSQL_USER for mysql user name<br>
MYSQL_PASSWORD for mysql user password<br>
MYSQL_HOST for mysql container name<br>
MYSQL_PORT for mysql port //note it running port 3306   <br>
<h5 id="admin_role">Admin gmail setup</h5><br>
GMAIL is important.Because it gmail only can do admin role.<br>

```text
    docker run -p 8080:8080 --name pos --net posnet -e MYSQL_PASSWORD=yourpassword -e MYSQL_HOST=mysql_container -e MYSQL_USER=root -e MYSQL_PORT=3306 -e GMAIL=sapaloo552@gmail.com -d posbackend:1.7
```
default <br>
MYSQL_USER=root <br>
MYSQL_PASSWORD=Mno2003 <br>
MYSQL_HOST=localhost <br>
MYSQL_PORT=3306  <br>
GMAIL=myonaingoo623@gmail.com

```text
    stop
    docker stop pos
    docker stop mysql_container

    run
    docker start mysql_container // we don't need one more then (docker run .... ) command 
    docker start pos

```











