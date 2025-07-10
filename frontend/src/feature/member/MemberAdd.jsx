import {
  Button,
  Col,
  FormControl,
  FormGroup,
  FormLabel,
  FormText,
  Row,
  Spinner,
} from "react-bootstrap";
import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export function MemberAdd() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [password2, setPassword2] = useState("");
  const [nickName, setNickName] = useState("");
  const [info, setInfo] = useState("");
  const [isProcessing, setIsProcessing] = useState(false);

  function handleSaveClick() {
    // post /api/member/add, {email, password, nickName, info}
    setIsProcessing(true);
    axios
      .post("/api/member/add", {
        email: email,
        password: password,
        nickName: nickName,
        info: info,
      })
      .then((res) => {
        console.log("good");
        const message = res.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .catch((err) => {
        console.log("bad");
        const message = err.response.data.message;
        if (message) {
          toast(message.text, { type: message.type });
        }
      })
      .finally(() => {
        console.log("always");
        setIsProcessing(false);
      });
  }

  // 이메일, 암호, 별명 입력하지 않으면 가입버튼 비활성화
  let disabled = false;
  if (email === "") {
    disabled = true;
  }
  if (password === "") {
    disabled = true;
  }
  if (nickName === "") {
    disabled = true;
  }
  // password와 password2가 일치하지 않으면 가입버튼 비활성화
  let passwordConfirm = true;
  if (password !== password2) {
    disabled = true;
    passwordConfirm = false;
  }

  return (
    <Row className="justify-content-center">
      <Col xs={12} md={8} lg={6}>
        <h2 className="mb-4">회원 가입</h2>
        <div>
          <FormGroup className="mb-3" controlId="email1">
            <FormLabel>이메일</FormLabel>
            <FormControl
              value={email}
              onChange={(e) => setEmail(e.target.value.trim())}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="password1">
            <FormLabel>암호</FormLabel>
            {/*type은 password인데 보이도록 text로 둠*/}
            <FormControl
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="password2">
            <FormLabel>암호 확인</FormLabel>
            {/*type은 password인데 보이도록 text로 둠*/}
            <FormControl
              type="password"
              value={password2}
              onChange={(e) => setPassword2(e.target.value)}
            />
            {passwordConfirm || (
              <FormText className="text-danger">
                패스워드가 일치하지 않습니다.
              </FormText>
            )}
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="nickName1">
            <FormLabel>별명</FormLabel>
            <FormControl
              value={nickName}
              onChange={(e) => setNickName(e.target.value.trim())}
            />
          </FormGroup>
        </div>
        <div>
          <FormGroup className="mb-3" controlId="info1">
            <FormLabel>자기 소개</FormLabel>
            <FormControl
              as="textarea"
              rows={6}
              value={info}
              onChange={(e) => setInfo(e.target.value)}
            />
          </FormGroup>
        </div>
        <div className="mb-3">
          <Button onClick={handleSaveClick} disabled={isProcessing || disabled}>
            {isProcessing && <Spinner size="sm" />}
            가입
          </Button>
        </div>
      </Col>
    </Row>
  );
}
