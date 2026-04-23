import axios from 'axios';

const API = axios.create({
    baseURL: 'http://localhost:8080/api'
});

export const convertCurrency = (data) => API.post('/conversions/convert', data);

export const getConversionHistory = () => API.get('/conversions/history');

export const fetchLatestRates = (baseCurrency = 'USD') => 
    API.post(`/exchange-rates/fetch?baseCurrency=${baseCurrency}`);

export const getAvailableCurrencies = () => API.get('/exchange-rates/currencies');