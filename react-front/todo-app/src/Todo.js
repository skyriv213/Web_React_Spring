import React from 'react';

class Todo extends React.Component{
    render(){
        return(
            <div className="Todo">
                <input type="checkbox" id="Todo0" name="Todo0" value="Todo0"/>
                <label for="Todo0">Todo 컴포넌트 만들기</label>
            </div>
        );
    }
}

export default Todo;
