import React from "react";
import Todo from "./Todo";
import './App.css';

class App extends React.Component{
  render(){
    return(
      <div className="App">
        <Todo />
      </div>
    );
  }
}

export default App;