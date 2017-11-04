var promise = require('bluebird');

var options = {
  // Initialization Options
  promiseLib: promise
};

var pgp = require('pg-promise')(options);
var connectionString = 'postgres://shopping:shopping@localhost:5432/shopping';
var db = pgp(connectionString);

// add query functions

module.exports = {
    getAllProducts: getAllProducts,
    getSingleProduct: getSingleProduct,
    createProduct: createProduct,
    updateProduct: updateProduct,
    removeProduct: removeProduct,
    register:register,
    login:login
};


function getAllProducts(req, res, next) {
  db.any('select * from products')
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
    db.one('select * from products where id = $1', productID)
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
    db.none('inser into users(username,password)values(${username},${password_cripted})',req.body)
    .then(function () {
        res.status(200)
          .json({
            status: 'success',
            message: 'User registered'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}
/* preciso de ver quais são os requests caso o user n esteja já registado
*/
function login(req,res,next){
    var username=req.params.username;
    db.one('select password from users where username=$1',username)
     .then(function (data) {
        res.status(200)
          .json({
            status: 'success',
            password: data,
            message: 'Username exists'
          });
      })
      .catch(function (err) {
        return next(err);
      });
}