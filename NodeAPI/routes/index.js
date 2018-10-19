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

router.post("/checkout", function (req, res) {
    var nonceFromTheClient = req.body.payment_method_nonce;
    var amountvalue=req.body.amount;
    // Use payment method nonce here
    gateway.transaction.sale({
        amount: amountvalue,
        paymentMethodNonce: nonceFromTheClient,
        options: {
            submitForSettlement: true
            storeInVaultOnSuccess: true
        }
    }, function (err, result) {

        if(err)
        {
            res.send({"error":"UnAuthorised Access Or Transaction"});

        }
        else if(result.success)
        {
            res.send({"Success":"Successfull Transaction Wait for Settlement"});


        }
    });
});


module.exports = router;
