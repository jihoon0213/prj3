import { BrowserRouter, Route, Routes } from "react-router";
import { MainLayout } from "./common/MainLayout.jsx";
import { BoardAdd } from "./feature/board/BoardAdd.jsx";
import { BoardList } from "./feature/board/BoardList.jsx";
import { BoardDetail } from "./feature/board/BoardDetail.jsx";
import { BoardEdit } from "./feature/board/BoardEdit.jsx";
import { MemberAdd } from "./feature/member/MemberAdd.jsx";
import { MemberList } from "./feature/member/MemberList.jsx";
import { MemberDetail } from "./feature/member/MemberDetail.jsx";
import { MemberEdit } from "./feature/member/MemberEdit.jsx";
import { MemberLogin } from "./feature/member/MemberLogin.jsx";
import { MemberLogout } from "./feature/member/MemberLogout.jsx";
import { AuthenticationContextProvider } from "./common/AuthenticationContextProvider.jsx";

function App() {
  return (
    <AuthenticationContextProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<BoardList />} />
            <Route path="board/add" element={<BoardAdd />} />
            <Route path="board/:id" element={<BoardDetail />} />
            <Route path="board/edit" element={<BoardEdit />} />
            <Route path="signup" element={<MemberAdd />} />
            <Route path="login" element={<MemberLogin />} />
            <Route path="logout" element={<MemberLogout />} />
            <Route path="member/list" element={<MemberList />} />
            <Route path="member" element={<MemberDetail />} />
            <Route path="member/edit" element={<MemberEdit />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthenticationContextProvider>
  );
}

export default App;
