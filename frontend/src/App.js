import React from 'react';
import { BrowserRouter, Outlet, Route, Routes } from 'react-router-dom';
import ScrollManager from './components/utils/ScrollManager';

import Home from './pages/Home';
import Header from './components/layout/Header';
import Footer from './components/layout/Footer';

import LoginForm from './pages/auth/LoginForm';
import SignUpForm from './pages/auth/SignUpForm';
import CustomerSupport from './features/policy/CustomerSupport';
import TermsOfService from './features/policy/TermsOfService';

import StoreDetail from './pages/store/StoreDetail';

import MyPage from './pages/mypage/MyPage';
import Profile from './features/mypage/content/Profile';
import Account from './features/mypage/content/Account';
import Like from './features/mypage/content/Like';
import Review from './features/mypage/content/Review';

/**
 * Header, Footer가 포함된 기본 레이아웃입니다.
 *
 * @param {JSX.Element} children 리액트 컴포넌트입니다.
 * @returns
 */
const DefaultLayout = () => (
  <>
    <Header />
    <main>
      <Outlet />
    </main>
    <Footer />
  </>
);

/**
 * Header, Footer가 없는 레이아웃입니다.
 *
 * @param {JSX.Element} children 리액트 컴포넌트입니다.
 * @returns
 */
const MinimalLayout = () => (
  <>
    <main>
      <Outlet />
    </main>
  </>
);

/**
 * App 전체 라우팅을 관리하는 컴포넌트입니다.
 * @returns {JSX.Element} - 전체 라우팅 구성
 */
function App() {
  return (
    <BrowserRouter>
      <ScrollManager />
      <Routes>
        {/* Header, Footer가 포함되는 컴포넌트 */}
        <Route element={<DefaultLayout />}>
          <Route path="/" element={<Home />} />
          <Route path="/support" element={<CustomerSupport />} />
          <Route path="/terms" element={<TermsOfService />} />

          <Route path="/store/:storeId" element={<StoreDetail />} />

          {/* mypage 라우트 -> 중앙 집중식 관리에 용이하여 App.js에 라우트 정의 */}
          <Route path="/mypage/*" element={<MyPage />}>
            <Route path="profile" element={<Profile />} />
            <Route path="account" element={<Account />} />
            <Route path="like" element={<Like />} />
            <Route path="review" element={<Review />} />
          </Route>
        </Route>

        {/* Header, Footer가 포함되지 않는 컴포넌트 */}
        <Route element={<MinimalLayout />}>
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignUpForm />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
