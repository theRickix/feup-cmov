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
  removeProduct: removeProduct
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
    db.none('insert into products(name, category, model, maker, price)' +
        'values(${name}, ${category}, ${model}, ${maker}, ${price})',
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
  db.none('UPDATE products SET name=$1, category=$2, model=$3, maker=$4, price=$5 where id=$6',
      [req.body.name, req.body.category, req.body.model,
        req.body.model, req.body.price, parseInt(req.params.id)])
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
