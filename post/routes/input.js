var express = require('express');
var router = express();
var mongo = require("./mongoset");
var mongof = require("./mongofind");

/* GET users listing. */
router.post('/', function(req, res, next) {
  var i = req.body.text;

  mongo.mongoset(i, i);
  console.log(i + " post内容");
  var all = mongof();
  console.log(all);
  //var all1 = mongofind();
  //var all1 = mongof.documenta;
  //console.log(all + " 取得データ0");
  //console.log(all1 + " 取得データ1");

  res.render('view', {
    title: i + 'をinsert 行いました！',
    data: all
  });
});
module.exports = router;
