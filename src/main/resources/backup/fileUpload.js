 /* $('#signup-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);

			var id = "${member.id}";
			var action = form.attr('action');
			var url = id === "" ? action : action + "/" + id;
			var type = id === "" ? 'POST' : 'PUT';
			var _csrf = $('#_csrf').val();

			if (console) {
				console.log(JSON.stringify(form.serializeObject()));
			}

			$.ajax({
				url : url,
				dataType : 'json',
				type : type,
				data : JSON.stringify(form.serializeObject()),
				cache : false,
				contentType : 'application/json',
				beforeSend : function(request) {
					request.setRequestHeader("X-CSRF-TOKEN", _csrf);
				},
				success : function(data) {
					if (data) {
						location.href = "/pages/home";
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
		});  */

var uploadFile; 
		uploadFile = function() {
			
			alert(223);
			var formData = new FormData();

			formData.append('profileImage', $('#signup-form')[0].profileImage.files[0]);
			formData.append('properties', new Blob([JSON.stringify({
			                'authName': 'root',
			                'password': 'root',
			                	'name': 'dddd'
			            })], {
			                type: 'application/json'
			            }));
			var _csrf = $('#_csrf').val();
		  $.ajax({
		    url: '/upload/images',
		    type: 'POST',
		    data: formData,
		    enctype: 'multipart/form-data',
		    processData: false,
		    contentType: false,
		    cache: false,
		    beforeSend : function(request) {
				request.setRequestHeader('X-CSRF-TOKEN', _csrf);
			},
		    success: function (data) {
		    	console.log(data);
		      // Handle upload success
		      $('#upload-file-message').text('File succesfully uploaded');
		     //$('#profileImageName').val(data.id);
		    },
		    error: function (xhr, status, err) {
		      // Handle upload error
		      console.log(xhr);
		      $('#upload-file-message').text(
		          "File not uploaded (perhaps it's too much big)");
		    }
		  });
		} 
		//$("#profileImage").on("change", uploadFile);
		
		
		//공통화 필요.
		// file upload 관련..참조(아래)
		// http://stackoverflow.com/questions/21329426/spring-mvc-multipart-request-with-json
		$('#club-form').submit(function(event) {
			event.preventDefault();
			var form = $(this);
			var formData = new FormData();
			var uploadFile = form[0].mainImage.files[0];

			var id = "${club.id}";
			var action = form.attr('action');
			var url = id === "" ? action : action + "/" + id;
			var type = id === "" ? 'POST' : 'PUT';
			var _csrf = $('#_csrf').val();

			if (console) {
				console.log(JSON.stringify(form.serializeObject()));
			}

			$.ajax({
				url : url,
				dataType : 'json',
				type : type,
				data : JSON.stringify(form.serializeObject()),
				cache : false,
				contentType : 'application/json',
				beforeSend : function(request) {
					request.setRequestHeader("X-CSRF-TOKEN", _csrf);
				},
				success : function(data) {
					if (data) {
						// TODO 추후에 개설된 클럽으로 이동할 수 있도록 변경. 
						location.href = "/pages/home";
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
		});
		