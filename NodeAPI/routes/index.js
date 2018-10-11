var express = require('express');
var router = express.Router();
var braintree = require("braintree");

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});
//BRAIN TREE SETUP

var gateway = braintree.connect({
    environment: braintree.Environment.Sandbox,
    merchantId: "649d9tf6hcfzfthk",
    publicKey: "r25dyy7cs5mgncmj",
    privateKey: "c73ea263ddc9ac92c6f3ef8e5219c7fb"
});
//genrating the client token
// gateway.clientToken.generate({
//     customerId: aCustomerId
// }, function (err, response) {
//     var clientToken = response.clientToken
// });


//sending the client token
router.get("/client_token", function (req, res) {
    gateway.clientToken.generate({}, function (err, response) {
        res.send({"token":response.clientToken});
    });
});
module.exports = router;
