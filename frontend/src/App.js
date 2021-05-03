import React from 'react';
import axios from "axios";

class App extends React.Component {
    constructor(props){
        super(props);
        this.state ={
            customers:[],
            id:0,
            firstName:'',
            lastName:'',
            email:'',
        }

    }
    componentDidMount(){
        axios.get("http://localhost:8080/customer/")
            .then((res)=>{
                this.setState({
                    customers:res.data,
                    id:0,
                    firstName:'',
                    lastName:'',
                    email:''
                })
            })
    }
    submit(event,id){
        event.preventDefault();
        if(id === 0){
            axios.post("http://localhost:8080/customer/",{
                firstName:this.state.firstName,
                lastName:this.state.lastName,
                email:this.state.email
            })
                .then((res)=>{
                    this.componentDidMount();
                })
        }else{
            axios.put(`http://localhost:8080/customer/${id}`,{
                firstName:this.state.firstName,
                lastName:this.state.lastName,
                email:this.state.email
            }).then(()=>{
                this.componentDidMount();
            })

        }

    }
    delete(id){
        axios.delete(`http://localhost:8080/customer/${id}`)
            .then(()=>{
                this.componentDidMount();
            })
    }
    edit(id){
        axios.get(`http://localhost:8080/customer/${id}`)
            .then((res)=>{
                console.log(res.data);
                this.setState({
                    id:res.data.id,
                    firstName:res.data.firstName,
                    lastName:res.data.lastName,
                    email:res.data.email
                })
            })
    }
    render(){
        return (
            <div className="container" >
                <br/>
                <div className="row">
                    <div className="col s6">
                        <form onSubmit={(e)=>this.submit(e,this.state.id)}>
                            <div class="input-field col s12">
                                <i class="material-icons prefix">person</i>
                                <input onChange={(e)=>this.setState({firstName:e.target.value})} value={this.state.firstName} type="text" id="autocomplete-input" class="autocomplete" />
                                <label form="autocomplete-input">Adınız:</label>
                            </div>

                            <div className="input-field col s12">
                                <i className="material-icons prefix">person</i>
                                <input onChange={(e) => this.setState({lastName: e.target.value})} value={this.state.lastName} type="text"
                                       id="autocomplete-input" className="autocomplete"/>
                                <label form="autocomplete-input">Soyadınız:</label>
                            </div>

                            <div class="input-field col s12">
                                <i class="material-icons prefix">email</i>
                                <input onChange={(e)=>this.setState({email:e.target.value})} value={this.state.email} type="email" id="autocomplete-input" class="autocomplete" />
                                <label form="autocomplete-input">Email</label>
                            </div>

                            <button class="btn waves-effect waves-light right" type="submit" name="action">Kaydet
                                <i class="material-icons right">send</i>
                            </button>


                        </form>
                    </div>
                    <div className="col s6">
                        <table>
                            <thead>
                            <tr>
                                <th>Ad</th>
                                <th>Soyad</th>
                                <th>Email</th>
                                <th>Değiştir</th>
                                <th>Sil</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                this.state.customers.map(customer=>
                                    <tr key={customer.id}>
                                        <td>{customer.firstName}</td>
                                        <td>{customer.lastName}</td>
                                        <td>{customer.email}</td>

                                        <td>
                                            <button onClick={(e)=>this.edit(customer.id)} class="btn waves-effect waves-light" type="submit" name="action">
                                                <i class="material-icons">edit</i>
                                            </button>
                                        </td>
                                        <td>
                                            <button onClick={(e)=>this.delete(customer.id)} class="btn waves-effect waves-light" type="submit" name="action">
                                                <i class="material-icons">delete</i>
                                            </button>
                                        </td>
                                    </tr>
                                )
                            }

                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        );
    }
}

export default App;