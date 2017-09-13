'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {redyes: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/redyes'}).done(response => {
			this.setState({redyes: response.entity});
		});
	}

	render() {
		return (
			<RedyeList redyes={this.state.redyes}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class RedyeList extends React.Component{
	render() {
		var redyes = this.props.redyes.map(redye =>
			<Redye key={redye.id} redye={redye}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Batch Id</th>
						<th>Redye? (1=Yes, 0=No)</th>
                        <th>Probability</th>
					</tr>
					{redyes}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::employee[]
class Redye extends React.Component{
	render() {
		return (
			<tr>
               <td>{this.props.redye.id}</td>
               <td>{this.props.redye.prediction.classes}</td>
               <td>{this.props.redye.prediction.probabilities}</td>


			</tr>
		)
	}
}
// end::employee[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]

