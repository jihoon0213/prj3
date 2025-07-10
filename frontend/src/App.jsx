import { BrowserRouter, Route, Routes } from "react-router";
import { MainLayout } from "./common/MainLayout.jsx";
import { BoardAdd } from "./feature/board/BoardAdd.jsx";
import { BoardList } from "./feature/board/BoardList.jsx";
import { BoardDetail } from "./feature/board/BoardDetail.jsx";
import { BoardEdit } from "./feature/board/BoardEdit.jsx";
import { MemberAdd } from "./feature/member/MemberAdd.jsx";
import { MemberList } from "./feature/member/MemberList.jsx";
import { MemberDetail } from "./feature/member/MemberDetail.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<BoardList />} />
          <Route path="board/add" element={<BoardAdd />} />
          <Route path="board/:id" element={<BoardDetail />} />
          <Route path="board/edit" element={<BoardEdit />} />
          <Route path="/signup" element={<MemberAdd />} />
          <Route path="/member/list" element={<MemberList />} />
          <Route path="/member" element={<MemberDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
