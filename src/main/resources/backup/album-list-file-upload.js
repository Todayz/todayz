var uploadFile = function(event) {
	var form = $("#album-form");
	var formData = new FormData();
	console.log("file");
	console.log(form[0].photo);
	console.log(form[0].photo.files);
	var uploadFile = form[0].photo.files[0];

	var action = form.attr('action');
	var url = action;
	// var type = id === "" ? 'POST' : 'PUT';
	url += "?clubId=${clubId}";
	var type = 'POST';
	var _csrf = $('#_csrf').val();

	if (console) {
		// console.log(JSON.stringify(form.serializeObject()));
		// console.log(uploadFile);
	}
	if (uploadFile) {
		formData.append('photo', uploadFile);
		console.log("formData");
		console.log(formData);
	}
	/*
	 * formData.append('album', new
	 * Blob([JSON.stringify(form.serializeObject())], { type: 'application/json'
	 * }));
	 */
	$.ajax({
		url : url,
		// dataType : 'json',
		type : type,
		data : formData,
		enctype : 'multipart/form-data',
		processData : false,
		contentType : false,
		cache : false,
		beforeSend : function(request) {
			request.setRequestHeader("X-CSRF-TOKEN", _csrf);
		},
		success : function(data) {
			if (data) {
				// console.log(data);
				// location.reload();
			}
		}.bind(this),
		error : function(xhr, status, err) {
			if (console) {
				console.log(xhr);
				console.log(status);
				console.log(err);
			}
		}.bind(this)
	});
} // function uploadFile
