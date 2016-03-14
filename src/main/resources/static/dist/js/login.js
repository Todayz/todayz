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
	    url: this.props.url,
	    dataType: 'json',
	    type:'POST',
	    data: form_data,
	    cache: false,
	    success: function(data) {
	    	if(data.success) {
	    			location.href = '/pages/index_react.html';
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
ReactDOM.render(
  <LoginBox url="/loginProcess" />,
  document.getElementById('login-box')
);