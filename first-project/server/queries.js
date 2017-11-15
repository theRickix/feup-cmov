var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

var pgp = require('pg-promise')(options);
var connectionString = 'postgres://postgres:shopping@localhost:5432/shopping';
var db = pgp(connectionString);

// add query functions

module.exports = {
    getAllProducts: getAllProducts,
    getSingleProduct: getSingleProduct,
    getSingleProductByBarcode: getSingleProductByBarcode,
    createProduct: createProduct,
    updateProduct: updateProduct,
    removeProduct: removeProduct,
    getUserId: getUserId,
    register:register,
    login:login,
    updateUserPublicKey:  updateUserPublicKey,
    getAllPurchases: getAllPurchases,
    insertPurchase: insertPurchase,
    insertPurchaseRow: insertPurchaseRow,
    getPurchaseFromToken: getPurchaseFromToken,
    getUserOfPurchase:getUserOfPurchase
  };


function getAllProducts(req, res, next) {
  db.any('select products.id as id, products.model as model, products.barcode as barcode, '+
          'makers.name as maker, categories.name as category, products.price as price FROM products '+
          'INNER JOIN makers ON makers.id = products.maker_id '+
          'INNER JOIN categories ON categories.id = products.category_id')
    .then(function (data) {
      res.status(200)
        .json({
          status: 'success',
          data: data,
          message: 'Retrieved ALL products'
        });
    })
    .catch(function (err) {
      return next(err);
    });
}

function getSingleProduct(req, res, next) {
  	var productID = parseInt(req.params.id);
    db.one('select products.id as id, products.model as model, products.barcode as barcode, '+
          'makers.name as maker, categories.name as category, products.price as price FROM products '+
          'INNER JOIN makers ON makers.id = products.maker_id '+
          'INNER JOIN categories ON categories.id = products.category_id where products.id = $1', productID)
      .then(function (data) {
        res.status(200)
          .json({
            status: 'success',
            data: data,
            message: 'Retrieved ONE product'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}

function getSingleProductByBarcode(req, res, next) {
    db.one('select products.id as id, products.model as model, products.barcode as barcode, '+
          'makers.name as maker, categories.name as category, products.price as price FROM products '+
          'INNER JOIN makers ON makers.id = products.maker_id '+
          'INNER JOIN categories ON categories.id = products.category_id where products.barcode = $1',  req.params.barcode)
      .then(function (data) {
        res.status(200)
          .json({
            status: 'success',
            data: [data],
            message: 'Retrieved ONE product'
          });
      })
      .catch(function (err) {
        res.status(500)
          .json({
            status: 'error'
          });
      });
}

function createProduct(req, res, next) {
    db.none('insert into products(model, maker_id, category_id, price)' +
        'values(${model}, ${maker_id}, ${category_id}, ${price})',
      req.body)
      .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'Inserted one product'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}

function updateProduct(req, res, next) {
  db.none('UPDATE products SET model=$1, maker_id=$2, category_id=$3, price=$4 where id=$5',
      [req.body.model, parseInt(req.body.maker_id), parseInt(req.body.category_id),
         req.body.price, parseInt(req.params.id)])
      .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'Updated price'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}

function removeProduct(req, res, next) {
  var product = parseInt(req.params.id);
    db.result('delete from products where id = $1', product)
      .then(function (result) {
        /* jshint ignore:start */
        res.status(200)
          .json({
            status: 'success',
            message: `Removed ${result.rowCount} product`
          });
        /* jshint ignore:end */
      })
      .catch(function (err) {
        return next(err);
      });
}

function register(req,res,next){
    db.none('insert into users(name,email,password,address,postal_code,fiscal,cc_type,cc_number,cc_expiry_month, cc_expiry_year,public_key) values(${name},${email},${password},${address},${postal_code},${fiscal},${cc_type},${cc_number},${cc_expiry_month}, ${cc_expiry_year},${public_key})',req.body)
    .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'Registration sucessful.'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}

function getUserId(req,res,next){
    db.one('SELECT * FROM users WHERE email=$1',req.params.email)
    .then(function () {
        res.status(200)
          .json({
            status: 'success',
            data: data,
            message: 'Registration sucessful.'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}

function login(req,res,next){
    var email=req.body.email;
    var password=req.body.password;
    db.one('select * from users WHERE email=$1 AND password=$2 ',[email,password])
     .then(function (data) {
        res.status(200)
          .json({
            status: 'success',
            data: [data],
            message: 'Login sucessful.'
          });
                 
      })
      .catch(function (err) {
        console.log(err);
        return next(err);
      });
}

function updateUserPublicKey(req, res, next) {
  db.none('UPDATE users SET public_key=$1 where id=$2',
      [req.body.public_key, parseInt(req.params.id)])
      .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'Updated public key'
          });
      })
      .catch(function (err) {
        console.log(err);
        return next(err);
      });
}

function insertPurchase(req, res, next) {
  db.one('INSERT INTO purchases(purchase_timestamp,user_id,validation_token) VALUES (CURRENT_TIMESTAMP,$1,md5(random()::text || clock_timestamp()::text)::uuid) RETURNING purchases.id, purchases.validation_token',parseInt(req.body.user_id))
   .then(function (data) {
      res.status(200)
        .json({
          id: data.id,
          validation_token: data.validation_token
        });
    })
    .catch(function (err) {
      console.log(err);
      return next(err);
    });
}

function insertPurchaseRow(req, res, next) {
    db.none('insert into purchase_rows(purchase_id,product_id) ' +
        'values(${purchase_id},${product_id})',req.body)
      .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'Inserted purchase_row'
          });
      })
      .catch(function (err) {
        return next(err);
        console.log(err);
      });
}

function getAllPurchases(req, res, next) {
    var userID = parseInt(req.params.id);
    db.any('select purchases.id as id, purchases.purchase_timestamp as purchase_timestamp, purchases.user_id as user_id, purchases.validation_token as validation_token, '+
      '(SELECT SUM(products.price) FROM products INNER JOIN purchase_rows ON '+
      'purchase_rows.product_id=products.id WHERE purchase_rows.purchase_id=purchases.id) as total_price from purchases WHERE purchases.user_id=$1', userID)
      .then(function (data) {
        res.status(200)
          .json({
            status: 'success',
            data: data,
            message: 'Retrieved all purchases from user'
          });
      })
      .catch(function (err) {
        return next(err);
        console.log(err);
      });
}
function getUserOfPurchase(req,res,next){
    var token=req.params.token;
    db.one('select users.name,users.email,users.address,users.postal_code,users.fiscal from users join purchases on (users.id=purchases.user_id and validation_token=$1)',token)
     .then(function(data){
    res.status(200)
    .json({
        status:'success',
        data:[data],
        message:'Retrieved your purchases belonging to this token'
    });
        console.log(data);
})
      .catch(function(err){
        return next(err);
        console.log(err);
    });    
    
}

function getPurchaseFromToken(req,res,next){
    var token=req.params.token;
    db.any(' select products.id,products.model,products.barcode,products.maker_id,products.category_id,products.price from products Join purchase_rows on products.id=purchase_rows.product_id Join purchases on (purchases.id = purchase_rows.purchase_id AND purchases.validation_token=$1)',token)
    .then(function(data){
    res.status(200)
    .json({
        status:'success',
        data:data,
        message:'Retrieved your purchases belonging to this token'
    });
        console.log(data);
})
      .catch(function(err){
        return next(err);
        console.log(err);
    });    
}
