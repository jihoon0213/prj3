import { Outlet } from "react-router";
import { AppNavBar } from "./AppNavBar.jsx";
import { Container } from "react-bootstrap";

export function MainLayout() {
  return (
    <div>
      <div className="mb-3">
        <AppNavBar />
      </div>
      <Container>
        <Outlet />
      </Container>
    </div>
  );
}
