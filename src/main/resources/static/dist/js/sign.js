var SignupBox = React.createClass({
  displayName: 'SignupBox',
  getInitialState: function() {
	  return  { authName: '',
				  password: '',
				  name: '',
				  phoneNumber: '',
				  birthday: '',
				  description: ''}
  },
  handleAuthNameChange: function(e) {
    this.setState({authName: e.target.value});
  },
  handlePasswordChange: function(e) {
    this.setState({password: e.target.value});
  },
  handleNameChange: function(e) {
    this.setState({name: e.target.value});
  },
  handlePhoneNumberChange: function(e) {
    this.setState({phoneNumber: e.target.value});
  },
  handleBirthdayChange: function(e) {
	 this.setState({birthday: e.target.value});
  },
  handleDescriptionChange: function(e) {
	 this.setState({description: e.target.value});
  },
  handleReset: function(e) {
	  e.preventDefault();
	  this.setState({ authName: '',
		  password: '',
		  name: '',
		  phoneNumber: '',
		  birthday: '',
		  description: ''});  
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var authName = this.state.authName.trim();
    var password = this.state.password.trim();
    var name = this.state.name.trim();
    var phoneNumber = this.state.phoneNumber.trim();
    var birthday = this.state.birthday.trim();
    var description = this.state.description.trim();
    if (!authName || !password || !name || !phoneNumber || !birthday || !description) {
       if(console) {
    	   console.log("validation messages");
       }
      return;
    }
    // TODO: send request to the server 
    // TODO: https or password encoding
    var form_data = {
    	authName: authName,
  		password: password,
  		name: name,
  		phoneNumber: phoneNumber,
  		birthday: birthday,
  		description: description
	};
    
    if(console) {
    	console.log(form_data);
    }

    $.ajax({
	    url: '/members',
	    dataType: 'json',
	    type:'POST',
	    data: JSON.stringify(form_data),
	    cache: false,
	    contentType: 'application/json',
	    success: function(data) {
	    	if(data) {
	    		console.log(data);
	    		location.hash = "#/";
	    	}
	      //this.setState({data: data});
	    }.bind(this),
	    error: function(xhr, status, err) {
	    	if(console) {
		    	console.log(xhr);
		    	console.log(status);
		    	console.log(err);
		    	console.error(this.props.url, status, err.toString());
	    	}
	    }.bind(this)
	  });
  },
  render: function() {
    return (
    <div className="container">
        <div className="row">
             <div className="col-md-4 col-md-offset-4">
                 <div classNameName="panel-heading">
                    <h3 classNameName="panel-title">Sign Up</h3>
                </div>
                <form role="form" onSubmit={this.handleSubmit} method="POST">
                    <div className="form-group" action="/members" method="POST">
                        <label>username</label>
                        <input className="form-control" name="authName" type="text"
                        		value={this.state.authName} onChange={this.handleAuthNameChange} autofocus />
                    </div>
                    <div className="form-group">
                        <label>password</label>
                        <input className="form-control" name="password" type="password" 
                        		value={this.state.password} onChange={this.handlePasswordChange} />
                    </div>
                    <div className="form-group">
                        <label>name</label>
                        <input className="form-control" name="name" type="text" 
                        		value={this.state.name} onChange={this.handleNameChange}  />
                    </div>
                    <div className="form-group">
                        <label>phone number</label>
                        <input className="form-control" name="phoneNumber" type="text"
                        		value={this.state.phoneNumber} onChange={this.handlePhoneNumberChange} />
                    </div>
                    <div className="form-group">
                        <label>birthday</label>
                        <input className="form-control" name="birthday" type="text" 
                        		value={this.state.birthday} onChange={this.handleBirthdayChange} />
                    </div>
                    <div className="form-group">
                        <label>description</label>
                        <textarea className="form-control" name="description" rows="3"
                        	 value={this.state.description} onChange={this.handleDescriptionChange}></textarea>
                    </div>
                    <div class="form-group">
	                    <label>profile image</label>
	                    <input type="file" name="profileImage" />
	                </div>
                    <button type="submit" className="btn btn-default">Submit Button</button>
                    <button type="reset" className="btn btn-default" onClick={this.handleReset}>Reset Button</button>
                </form>
            </div>
        </div>
    </div>
    );
  }
});

var LoginBox = React.createClass({
  displayName: 'LoginBox',
  getInitialState: function() {
	    return {username: '', password: ''};
  },
  handleUsernameChange: function(e) {
    this.setState({username: e.target.value});
  },
  handlePasswordChange: function(e) {
    this.setState({password: e.target.value});
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var username = this.state.username.trim();
    var password = this.state.password.trim();
    if (!username || !password) {
      return;
    }
    alert(username + " " + password);
    // TODO: send request to the server
    var form_data = {
		username: username,
		password: password
	};
    $.ajax({
	    url: '/loginProcess',
	    dataType: 'json',
	    type:'POST',
	    data: form_data,
	    cache: false,
	    success: function(data) {
	    	if(data.success) {
	    			location.href = '/pages/index.html';
	    	}
	      //this.setState({data: data});
	    }.bind(this),
	    error: function(xhr, status, err) {
	    	if(console) {
		    	console.log(xhr);
		    	console.log(status);
		    	console.log(err);
		    	console.error(this.props.url, status, err.toString());
	    	}
	    }.bind(this)
	  });
    this.setState({username: '', password: ''});
  },
  render: function() {
    return (
    <div className="container">
        <div className="row">
            <div className="col-md-4 col-md-offset-4">
                <div className="login-panel panel panel-default">
                    <div className="panel-heading">
                        <h3 className="panel-title">Please Sign In</h3>
                        	<a href="#/signup">move signup</a>
                    </div>
                    <div className="panel-body">
                        <form role="form" onSubmit={this.handleSubmit} method="POST">
                            <fieldset>
                                <div className="form-group">
                                    <input className="form-control" placeholder="authName" 
                                    	name="username" type="text" 
                                    	value={this.state.username} onChange={this.handleUsernameChange}/>
                                </div>
                                <div className="form-group">
                                    <input className="form-control" placeholder="Password" name="password" type="password" 
                                    		value={this.state.password} onChange={this.handlePasswordChange}/>
                                </div>
                                <div className="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me"/>Remember Me
                                    </label>
                                </div>
                                <div className="form-group">
	                                 <input name="submit" type="submit" value="Login"/>
                                 </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    );
  }
});

var routes = (
  <ReactRouter.Router>
    <ReactRouter.Route name="login" path="/" component={LoginBox}/> 
    <ReactRouter.Route name="signup" path="/signup" component={SignupBox}/> 
  </ReactRouter.Router>
);

ReactDOM.render(
    <ReactRouter.Router>{routes}</ReactRouter.Router>,
    document.getElementById('login-box')
);