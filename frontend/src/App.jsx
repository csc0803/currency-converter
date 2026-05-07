import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import { useState } from 'react';
import Navbar from './components/layout/Navbar';
import HomePage from './pages/HomePage';
import HistoryPage from './pages/HistoryPage';  
import ConvertAllPage from './pages/ConvertAllPage';

export default function App() {
  
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path='/' element={<HomePage />} />
        <Route path='/convert-all' element={<ConvertAllPage />} />
        <Route path='/history' element={<HistoryPage />} />
      </Routes>
    </BrowserRouter>
  );
}