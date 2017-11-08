	var express = require('express');
var router = express.Router();

var db = require('../queries');


router.get('/api/products', db.getAllProducts);
router.get('/api/products/id=:id', db.getSingleProduct);
router.get('/api/products/barcode=:barcode', db.getSingleProductByBarcode);
router.post('/api/products', db.createProduct);
router.put('/api/products/:id', db.updateProduct);
router.delete('/api/products/:id', db.removeProduct);
router.post('/api/users',db.register);
router.post('/api/users/login',db.login);
router.get('/api/users/email=:email',db.getUserId);
router.put('/api/users/update/:id',db.updateUserPublicKey);
router.post('/api/purchase/',db.updatePurchase);
router.post('/api/purchase/row',db.updatePurchaseRow);
module.exports = router;
