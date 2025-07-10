import {
  Button,
  Col,
  FormControl,
  FormGroup,
  FormLabel,
  Modal,
  Row,
  Spinner,
} from "react-bootstrap";
import { useNavigate, useSearchParams } from "react-router";
import { useEffect, useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export function MemberEdit() {
  const [member, setMember] = useState(null);
  const [modalShow, setModalShow] = useState(false);
  const [password, setPassword] = useState("");
  const [params] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`/api/member?email=${params.get("email")}`)
      .then((res) => {
        console.log("good");
        setMember(res.data);
      })
      .catch((err) => {
        console.log("bad");
      })
      .finally(() => {
        console.log("always");
      });
  }, []);

  function handleSaveButtonClick() {
    axios
      .put(`/api/member`, { ...member, password: password })
      .then((res) => {
        const message = res.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
        navigate(`/member?email=${member.email}`);
      })
      .catch((err) => {
        const message = err.response.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .finally(() => {
        setModalShow(false);
        setPassword("");
      });
  }

  if (!member) {
    return <Spinner />;
  }

  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">회원 정보 수정</h2>
        <div>
          <FormGroup controlId="email1" className="mb-3">
            <FormLabel>이메일</FormLabel>
            <FormControl disabled value={member.email} />
          </FormGroup>
        </div>
        <div>
          <FormGroup controlId="nickName1" className="mb-3">
            <FormLabel>별명</FormLabel>
            <FormControl
              value={member.nickName}
              onChange={(e) =>
                setMember({ ...member, nickName: e.target.value })
              }
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup controlId="info1" className="mb-3">
            <FormLabel>자기소개</FormLabel>
            <FormControl
              as="textarea"
              value={member.info}
              onChange={(e) => setMember({ ...member, info: e.target.value })}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup controlId="inserted1" className="mb-3">
            <FormLabel>가입일시</FormLabel>
            <FormControl
              type="datetime-local"
              disabled
              value={member.insertedAt}
            />
          </FormGroup>
        </div>
        <div>
          <Button
            className="me-2"
            variant="outline-secondary"
            onClick={() => navigate(-1)}
          >
            취소
          </Button>
          <Button variant="primary" onClick={() => setModalShow(true)}>
            저장
          </Button>
        </div>
      </Col>

      {/*   수정 확인 모달 */}
      <Modal show={modalShow} onHide={() => setModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>회원 정보 수정 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <FormGroup controlId="password1">
            <FormLabel>암호</FormLabel>
            <FormControl
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            ></FormControl>
          </FormGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-dark" onClick={() => setModalShow(false)}>
            취소
          </Button>
          <Button variant="primary" onClick={handleSaveButtonClick}>
            저장
          </Button>
        </Modal.Footer>
      </Modal>
    </Row>
  );
}
