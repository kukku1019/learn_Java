module.exports=function() {
  var obj = {};
  var MongoClient = require("mongodb").MongoClient;
  //var JSON=require("json");
  var documenta;
  // 接続文字列
  var url = "mongodb://localhost:27017/text";
  // MongoDB へ 接続
  MongoClient.connect(url, (error, db) => {
    var collection;
    // コレクションの取得
    collection = db.collection("text");
    // コレクションに含まれるドキュメントをすべて取得
    collection.find().toArray(funciton(error, documents) => {
      // for (var document of documents) {
      //   //console.log(document);
      // }
      //console.log(documents);
      var retsu = JSON.stringify(documents);

      obj = JSON.parse(retsu);
      //console.log(obj);
      console.log(obj);
        callback(obj);
    });
    // // コレクション中で条件に合致するドキュメントを取得
    // collection.find({price: {$lt: 2000}}).toArray((error, documents)=>{
    //     for (var document of documents) {
    //         console.log(document.name);
    //     }
    // });

  });

};
