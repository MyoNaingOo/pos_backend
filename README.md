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
        <a href="#price" >Price</a>
    </li>
    <li>
        <a href="#store_obj" >Store CRUD</a>
    </li>
    <li>
        <a href="#sale_obj" >Sale CRUD</a>
    </li>
    <li>
        <a href="#img" >APIs images</a>
    </li>
</ul>

<img src="./images/posg.gif" height="auto" width="100%" >

<h3 id="user_reg">User Register</h3>

Register :  http://localhost:8080/api/auth/register <br>
you can get Owner role <a href="#owner_role"> click here</a><br>
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
  "role": "USER",
  "nameNotAvailable": true,
  "gmailNotAvailable": false
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
    "role": "USER",
    "enabled": true,
    "accountNonLocked": true,
    "username": "**@gmail",
    "authorities": [
      {
        "authority": "USER"
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
Add shop information of user  : http://localhost:8080/api/v2/user/change/shop?new_user=true (new user true for now
registering user)<br>

```json
{
  "shop": {
    "id": 1
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
  "number": 20,
  //result number
  "page_number": 1
  // pagination action number 
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
http://localhost:8080/api/v2/user/shop/page <br>

Logout Acc : http://localhost:8080/api/auth/logout GET Method<br>
Delete My Acc : http://localhost:8080/api/v2/user/delete <br>

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

Image update: http://localhost:8080/api/v2/user/image
PUT request :

```json
{
  "user_img": "***"
  // file name 
}
```

Name update: http://localhost:8080/api/v2/user/changeName
PUT request :

```json
{
  "name": "***"
}
```

password update: http://localhost:8080/api/v2/user/changePass
PUT request :

```json
{
  "password": "***"
}
```

Shop update:   : http://localhost:8080/api/v2/user/change/shop?new_user=false  (registered user) <br>

```json
{
  "shop": {
    "id": 1
  }
}

```

User Manger control panel
Get Users info of shop : http://localhost:8080/api/v2/panel/user/shop/page/${num} <br>
http://localhost:8080/api/v2/panel/user/shop/page <br>
Get Users info : http://localhost:8080/api/v2/panel/userpage/${num} <br>
http://localhost:8080/api/v2/panel/user/page <br>
change role : http://localhost:8080/api/v2/panel/user/change/role <br>
ROLE:

    SALEWORKER,
    SALEMANAGER,
    STOREWORKER,
    STOREMANAGER,
    USERMANAGER,
    USER

CEO,OWNER can not change
set ceo role change for only owner

SetCEO : http://localhost:8080/api/v2/panel/user/setCEO <br>
Delete user : http://localhost:8080/api/v2/user/delete/{id} <br>
get user info : http://localhost:8080/api/v2/user/userid/{id} <br>

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

Product add     :  http://localhost:8080/api/v2/shop/product/add
request obj

```json
{
  "name": "",
  "img": "",
  "description": ""
}

```

Price add     :  http://localhost:8080/api/v2/price/add
request obj

```json
{
  "product_id": 1,
  "org_price": 130,
  "promo_price": 120,
  "purchase_price": 100
}
```

Product delete  :  http://localhost:8080/api/v2/product/delete/${id} id = product_id DELETE/Method<br>
but Product has been sale,store,price data added not available delete <br>

product_details : http://localhost:8080/api/v2/shop/product/pid/${id} (id = product_id )<br>
find by code : http://localhost:8080/api/v2/shop/product/find/code/{code}

response <a href="#product_obj" >Product Obj</a> <br>

products : http://localhost:8080/api/v2/shop/product/page/{num} <br>
http://localhost:8080/api/v2/shop/product/page

find by name and description : http://localhost:8080/api/v2/shop/product/find/{value}/{num}
http://localhost:8080/api/v2/shop/product/find/{value}/page

Sale Manager , Store Manager ,CEO,OWNER

product_details : http://localhost:8080/api/v2/panel/product/pid/${id} (id = product_id )<br>

products : http://localhost:8080/api/v2/panel/product/page/{num} <br>
http://localhost:8080/api/v2/panel/product/page

find by name and description : http://localhost:8080/api/v2/panel/product/find/{value}/{num}
http://localhost:8080/api/v2/panel/product/find/{value}/page

products find by
month : http://localhost:8080/api/v2/panel/product/findByMonth/${num}?month=${month_num}&year=${year_num} <br>
http://localhost:8080/api/v2/panel/product/findByMonth/page?month=${month_num}&year=${year_num} <br>

find by name and description : http://localhost:8080/api/v2/panel/product/find/{value}/{num}
http://localhost:8080/api/v2/shop/product/find/{value}/page

find by code : http://localhost:8080/api/v2/panel/product/find/code/{code}

shop filter
product_details : http://localhost:8080/api/v2/panel/product/shop/pid/${id}?shop_id={id} (id = product_id )<br>

products : http://localhost:8080/api/v2/panel/product/shop/page/{num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/product/shop/page?shop_id={id}

find by name and description : http://localhost:8080/api/v2/panel/product/shop/find/{value}/{num}?shop_id={id}
http://localhost:8080/api/v2/panel/product/shop/find/{value}/page?shop_id={id}

find by code : http://localhost:8080/api/v2/panel/product/shop/find/code/{code}

products find by
month : http://localhost:8080/api/v2/panel/product/shop/findByMonth/${num}?month=${month_num}&year=${year_num} <br>
http://localhost:8080/api/v2/panel/product/shop/findByMonth/page?month=${month_num}&year=${year_num} <br>

<h3 id="price">Price</h3>
Price add     :  http://localhost:8080/api/v2/price/add
request obj

```json
{
  "product_id": 1,
  "org_price": 130,
  "promo_price": 120,
  "purchase_price": 100
}
```

Prices     :  http://localhost:8080/api/v2/price/page/{num}
http://localhost:8080/api/v2/price/page

Prices filter by product :  http://localhost:8080/api/v2/price/product/page/{num}
http://localhost:8080/api/v2/price/product/page

Price delete  :  http://localhost:8080/api/v2/price/delete/${id} id = product_id DELETE/Method<br>

<h4 id="store_obj" >Store obj:</h4>

```json
{
  "id": "",
  "product": {
    ...
  },
  "user": {
    ...
  },
  "quantity": 200,
  "time": "",
  "update_quantity": 0
}
```

Store add : http://localhost:8080/api/v2/shop/store/add <br>
request obj:

```json
{
  "product_id": 1,
  "quantity": 300
}
```

Store Worker,Store Manager
Stores : http://localhost:8080/api/v2/shop/store/page/${num} <br>
http://localhost:8080/api/v2/shop/store/page <br>
Stores products balance : http://localhost:8080/api/v2/shop/store/prosBalance/${num} <br>
http://localhost:8080/api/v2/shop/store/prosBalance/page <br>
Stores sold out products : http://localhost:8080/api/v2/shop/store/sold/${num} <br>
http://localhost:8080/api/v2/shop/store/sold/page <br>

Stores findAll By
Monthly : http://localhost:8080/api/v2/shop/store/findAllByMonth/${num}?month=${month_num}&year=${year_num} <br>
http://localhost:8080/api/v2/shop/store/findAllByMonth/page?month=${month_num}&year=${year_num} <br>

findAll Stores By Product : http://localhost:8080/api/v2/shop/store/findAllByProduct/${num} <br>
http://localhost:8080/api/v2/shop/store/findAllByProduct/page <br>

Stores By User : http://localhost:8080/api/v2/shop/store/findAllByUser/${num} <br>
http://localhost:8080/api/v2/shop/store/findAllByUser/page <br>

Store delete : http://localhost:8080/api/v2/shop/store/delete/${id} id=product_id <br>

Store Manager

Stores : http://localhost:8080/api/v2/panel/store/page/${num} <br>
http://localhost:8080/api/v2/panel/store/page <br>
Stores products balance : http://localhost:8080/api/v2/panel/store/prosBalance/${num} <br>
http://localhost:8080/api/v2/panel/store/prosBalance/page <br>
Stores sold out products : http://localhost:8080/api/v2/panel/store/sold/${num} <br>
http://localhost:8080/api/v2/panel/store/sold/page <br>
Stores findAll By
Monthly : http://localhost:8080/api/v2/panel/store/findAllByMonth/${num}?month=${month_num}&year=${year_num} <br>
http://localhost:8080/api/v2/panel/store/findAllByMonth/page?month=${month_num}&year=${year_num} <br>

findAll Stores By Product : http://localhost:8080/api/v2/panel/store/findAllByProduct/${num} <br>
http://localhost:8080/api/v2/panel/store/findAllByProduct/page <br>

Stores By User : http://localhost:8080/api/v2/panel/store/findAllByUser/${num} <br>
http://localhost:8080/api/v2/panel/store/findAllByUser/page <br>

shop filter

Stores : http://localhost:8080/api/v2/panel/store/shop/page/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/page?shop_id={id} <br>
Stores products balance : http://localhost:8080/api/v2/panel/store/shop/prosBalance/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/prosBalance/page?shop_id={id} <br>
Stores sold out products : http://localhost:8080/api/v2/panel/store/shop/sold/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/sold/page?shop_id={id} <br>
Stores findAll By
Monthly : http://localhost:8080/api/v2/panel/store/shop/findAllByMonth/${num}?month=${month_num}&year=${year_num}&shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/findAllByMonth/page?month=${month_num}&year=${year_num}&shop_id={id} <br>

findAll Stores By Product : http://localhost:8080/api/v2/panel/store/shop/findAllByProduct/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/findAllByProduct/page?shop_id={id} <br>

Stores By User : http://localhost:8080/api/v2/panel/store/shop/findAllByUser/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/store/shop/findAllByUser/page?shop_id={id} <br>





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
    "shop": {
      ...
    },
    "role": "USER"
  },
  "saleProList": [
    {
      "product_id": 1,
      "quantity": 200
    },
    {
      "product_id": 2,
      "quantity": 200
    }
  ],
  "perishable":false,
  "time": ""
}


```

Sale add : http://localhost:8080/api/v2/shop/sale/add <br>
default(?perishable=false)

you can available, perishable products add : http://localhost:8080/api/v2/shop/sale/add?perishable=true <br>

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
  ],
  "perishable": false //default false for sale products
  // true for perishable products
}

```

Sale worker,Sale Manager
Sale with page : http://localhost:8080/api/v2/shop/sale/page/${num} <br>
http://localhost:8080/api/v2/shop/sale/page <br>
Sale findByMonth : http://localhost:8080/api/v2/shop/sale/findByMonth/${num}?month=${month_num}&year=${year_num} <br>
Sale findByMonth : http://localhost:8080/api/v2/shop/sale/findByMonth/page?month=${month_num}&year=${year_num} <br>
Sale delete : http://localhost:8080/api/v1/sale/delete/${id} <br>

Sale Manger
Sale with page : http://localhost:8080/api/v2/panel/sale/page/${num} <br>
http://localhost:8080/api/v2/panel/sale/page <br>
Sale findByMonth : http://localhost:8080/api/v2/panel/sale/findByMonth/${num}?month=${month_num}&year=${year_num} <br>
Sale findByMonth : http://localhost:8080/api/v2/panel/sale/findByMonth/page?month=${month_num}&year=${year_num} <br>

shop filter
Sale with page : http://localhost:8080/api/v2/panel/sale/shop/page/${num}?shop_id={id} <br>
http://localhost:8080/api/v2/panel/sale/shop/page?shop_id={id} <br>
Sale
findByMonth : http://localhost:8080/api/v2/panel/sale/shop/findByMonth/${num}?month=${month_num}&year=${year_num}&shop_id={id} <br>
Sale
findByMonth : http://localhost:8080/api/v2/panel/sale/shop/findByMonth/page?month=${month_num}&year=${year_num}?shop_id={id} <br>

sale product all get APIs perishable defaultValue value is all (?perishable=all)
other value{?perishable=true / ?perishable=false}

<h4 id="docker" >Docker image</h4>

step 1

check tag version <a href="https://hub.docker.com/r/myonaingoo/posbackend/tags" >More details</a>

```text
    docker pull myonaingoo/posbackend:2
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
<h5 id="owner_role">Owner gmail setup</h5><br>
GMAIL is important.Because it gmail only can do Owner role.<br>

```text
    docker run -p 8080:8080 --name pos --net posnet -e MYSQL_PASSWORD=yourpassword -e MYSQL_HOST=mysql_container -e MYSQL_USER=root -e MYSQL_PORT=3306 -e GMAIL=sapaloo552@gmail.com -d posbackend:2
```

default allowed URLs{
"http://localhost:4200/",
"http://localhost:3000/",
"http://localhost:8080/",
"http://localhost:5173/"
}

you can do. your custom port and host allowed (FE=http://localhost:1234/)
```text
    docker run -p 8080:8080 --name pos --net posnet -e MYSQL_PASSWORD=yourpassword -e MYSQL_HOST=mysql_container -e MYSQL_USER=root -e MYSQL_PORT=3306 -e GMAIL=sapaloo552@gmail.com  FE=http://localhost:1234/ -d posbackend:1.7
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


Role access
<img src="./images/posrole.jpg" width="100%" height="auto">


<h3 id="img" >APIs images</h3>
<img src="./images/pos1.jpg" width="100%" height="auto">
<img src="./images/pos2.jpg" width="100%" height="auto">
<img src="./images/pos3.jpg" width="100%" height="auto">
<img src="./images/pos4.jpg" width="100%" height="auto">
<img src="./images/pos5.jpg" width="100%" height="auto">
<img src="./images/pos6.jpg" width="100%" height="auto">
<img src="./images/pos7.jpg" width="100%" height="auto">
<img src="./images/pos8.jpg" width="100%" height="auto">
<img src="./images/pos9.jpg" width="100%" height="auto">
you can download pdf file it images.
<a href="./images/pos.pdf" >Download</a>
<br>
<hr>
<p style="text-align:center;">2024 &copy; </p>







