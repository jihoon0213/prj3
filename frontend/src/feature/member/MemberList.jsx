import { Col, Row, Spinner, Table } from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";

export function MemberList() {
  const [memberList, setMemberList] = useState(null);

  useEffect(() => {
    axios
      .get("/api/member/list")
      .then((res) => {
        setMemberList(res.data);
      })
      .catch((err) => {
        console.log("bad");
      })
      .finally(() => {
        console.log("always");
      });
  }, []);

  if (!memberList) {
    return <Spinner />;
  }

  return (
    <Row>
      <Col>
        <h2 className="mb-4">회원 목록</h2>
        {memberList.length > 0 ? (
          <Table striped hover>
            <thead>
              <tr>
                <th>EMAIL</th>
                <th>별명</th>
                <th>가입일시</th>
              </tr>
            </thead>
            <tbody>
              {memberList.map((member) => (
                <tr key={member.email}>
                  <td>{member.email}</td>
                  <td>{member.nickName}</td>
                  <td>{member.insertedAt}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          <p>회원이 없습니다.</p>
        )}
      </Col>
    </Row>
  );
}
