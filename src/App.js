import React from "react";
import "./App.css";
import {
  BrowserRouter as Router,
  Route,
  Routes,
} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import NavBar from "./navbar/NavBar";
import LoginScreen from "./pages/Login";
import SignupScreen from "./pages/Signup";
import AllScreen from "./pages/Allboard";
import BestScreen from "./pages/Bestboard";
import HonorScreen from "./pages/Honorboard";
import SearchPage from "./pages/SearchPage";
import Recipeform from "./pages/RecipeForm";
import RecipeDetail from "./components/RecipeDetail";
import InquiryBoard from './pages/InquiryBoard';
import InquiryWrite from './pages/InquiryWrite';
import InquiryDetail from './pages/InquiryDetail';

function App() {
  return (
    <div className="App">
      <Router>
        <NavBar />
        <Routes>
          <Route path="/search" element={<SearchPage />} />
          <Route path="/" element={<AllScreen />} />
          <Route path="/login" element={<LoginScreen />} />
          <Route path="/signup" element={<SignupScreen />} />
          <Route path="/Allboard" element={<AllScreen />} />
          <Route path="/Bestboard" element={<BestScreen/>} />
          <Route path="/Honorboard" element={<HonorScreen />} />
          <Route path="/recipe/:recipeId" element={<RecipeDetail />} />
          <Route path="/recipeform" element={<Recipeform />} />
          <Route path="/inquiry" element={<InquiryBoard />} />
          <Route path="/inquiry/:inquiryNum" element={<InquiryDetail />} />
          <Route path="/inquiry/write" element={<InquiryWrite />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
