import './App.css';

import { BrowserRouter, Route } from 'react-router-dom';

import Home from './views/Home';

function App() {

  return (

    <BrowserRouter>

      <Route path="/" component={Home} exact />

    </BrowserRouter>

  );
}

export default App;
