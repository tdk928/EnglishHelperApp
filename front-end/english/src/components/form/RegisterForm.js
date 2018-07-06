import React, { Component } from 'react';

// import validationFunc from './../../utils/formValidator';
import Input from './formFields/Input';


class RegisterForm extends Component {
	constructor() {
		super();

		this.state = {
			username: '',
			password: ''
		};
	}


	submitRegister(e) {
		e.preventDefault();
		let payload = {
			username: this.state.username,
			password: this.state.password,
		};
		this.signup(payload);
	}

	signup(payload) {
		fetch('http://localhost:8000/register', {
			method: 'POST',
			headers: {
				Accept: 'application/json',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(payload)
		})
			.then(res => {
				return res;
			})
	}

	
	render() {
		return (
			<form className="signup-form" onSubmit={this.submitRegister.bind(this)}>
				<fieldset className="App">
					<div style={{ display: 'inline-grid' }}>
						<h2>Register user</h2>
						<Input
							type="text"
							data="username"
							name="Username"
							func={e => {
								this.setState({ username: e.target.value });
							}}
							// valid={validObj.validMail}
						/>

						<Input
							type="password"
							data="password"
							name="Password"
							func={e => {
								this.setState({ password: e.target.value });
							}}
							// valid={validObj.validMail}
						/>

						<input
							className={'btn btn-primary'}
							type="submit"
							value="Create My Account"
							onClick={this.handleClick}
						/>
					</div>
				</fieldset>
			</form >
		);
	}
}

export default RegisterForm;
