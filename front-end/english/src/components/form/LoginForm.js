import React, { Component } from 'react';

import Input from './formFields/Input';


class LoginForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: ''
        };
    }



    submitLogin = e => {
        e.preventDefault();
        let payload = {
            username: this.state.username,
            password: this.state.password
        };

        if (payload.username && payload.password) {
            this.login(payload);
            this.setState({ err: false });
        } else {
            this.setState({ message: 'Please fill all the fileds', err: true });
        }
    };
    login = payload => {
        fetch('http://localhost:8000/login', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'content-type': 'application/json'
            },
            body: JSON.stringify(payload)
        })
            .then(res => {
                console.log(res)
                return res;
            })
            .then(result => {


                if (result.ok) {

                    this.setState({ message: result.message, err: false }, () => {
                        this.props.logged(result);
                        localStorage.setItem('token', result.token);
                        localStorage.setItem('user', result.username);
                    });
                } else {
                    this.setState({ message: result.message, err: true })
                    console.log(result.message)
                }
                // this.setState({ message: result.message, err: false }, () => {
                //     this.props.logged(result);
                //     localStorage.setItem('token', result.token);
                //     localStorage.setItem('user', result.username);
                // });
            });
    };
    render() {

        return (
            <form className="login-form" onSubmit={this.submitLogin}>
                <fieldset className="App">
                    <div style={{ display: 'inline-grid' }}>
                        <h2>Login</h2>

                        <Input
                            type="text"
                            data="login-username"
                            name="Username"
                            func={e => {
                                this.setState({ username: e.target.value });
                            }}
                        />

                        <Input
                            type="password"
                            data="login-password"
                            name="Password"
                            func={e => {
                                this.setState({ password: e.target.value });
                            }}
                        />
                        <input className="btn btn-primary mt-3" type="submit" value="Login to my Account" />
                    </div>
                </fieldset>
            </form>
        );
    }
}

export default LoginForm
