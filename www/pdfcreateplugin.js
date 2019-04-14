window.makepdf = function() {
	var options = {};
	options.message = "aye";
	options.duration = "karamba";
    cordova.exec(function(){
		console.log("Success!");
	}, function(err) {
		console.log(err);
    }, "PDFCreate", "createpdf", [options]);
};