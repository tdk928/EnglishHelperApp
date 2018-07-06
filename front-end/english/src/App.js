import React, { Component } from 'react';
import LoginForm from './components/form/LoginForm';
import './App.css';
import RegisterForm from './components/form/RegisterForm';

class App extends Component {
  constructor() {
    super();

    this.state = {
      username: localStorage.getItem('user'),
      token: localStorage.getItem('token')
    };
  }

  setUserInfo = info => {
    this.setState({ user: info.username, token: info.token });
  };

  render() {
    return (
      <div>
        <RegisterForm />
        <LoginForm logged={this.setUserInfo} />
      </div>
    );
  }
}

export default App;
