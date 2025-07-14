import { Col, Pagination, Row, Spinner, Table } from "react-bootstrap";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useSearchParams } from "react-router";

export function BoardList() {
  const [boardList, setBoardList] = useState(null);
  const [pageInfo, setPageInfo] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    // 마운트될때(initial render 시) 실행되는 코드
    axios
      .get(`/api/board/list?${searchParams}`)
      .then((res) => {
        console.log("잘 될 때 코드");
        setBoardList(res.data.boardList);
        setPageInfo(res.data.pageInfo);
      })
      .catch((err) => {
        console.log("잘 안될 때 코드");
      })
      .finally(() => {
        console.log("항상 실행 코드");
      });
  }, [searchParams]);

  function handleTableRowClick(id) {
    // 게시물 보기로 이동
    navigate(`/board/${id}`);
  }

  // 아직 게시물 없으면 스피너 돌리기
  if (!boardList) {
    return <Spinner />;
  }

  const pageNumbers = [];
  for (let i = pageInfo.leftPageNumber; i <= pageInfo.rightPageNumber; i++) {
    pageNumbers.push(i);
  }

  function handlePageNumberClick(pageNumber) {
    // console.log(pageNumber + "페이지로 이동");
    // navigate(`/?p=${pageNumber}`);
    const nextSearchParams = new URLSearchParams(searchParams);
    nextSearchParams.set("p", pageNumber);
    setSearchParams(nextSearchParams);
  }

  return (
    <>
      <Row>
        <Col>
          <h2 className="mb-4">글 목록</h2>
          {boardList.length > 0 ? (
            <Table striped={true} hover={true}>
              <thead>
                <tr>
                  <th style={{ width: "90px" }}>#</th>
                  <th>제목</th>
                  <th
                    className="d-none d-md-table-cell"
                    style={{ width: "200px" }}
                  >
                    작성자
                  </th>
                  <th
                    className="d-none d-lg-table-cell"
                    style={{ width: "200px" }}
                  >
                    작성일시
                  </th>
                </tr>
              </thead>
              <tbody>
                {boardList.map((board) => (
                  <tr
                    key={board.id}
                    style={{ cursor: "pointer" }}
                    onClick={() => handleTableRowClick(board.id)}
                  >
                    <td>{board.id}</td>
                    <td>{board.title}</td>
                    <td className="d-none d-md-table-cell">{board.nickName}</td>
                    <td className="d-none d-lg-table-cell">{board.timesAgo}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <p>
              작성된 글이 없습니다. <br />새 글을 작성해 보세요.
            </p>
          )}
        </Col>
      </Row>
      {/* 페이지 네이션 */}
      <Row className="my-3">
        <Col>
          <Pagination className="justify-content-center">
            <Pagination.First
              disabled={pageInfo.currentPageNumber === 1}
              onClick={() => handlePageNumberClick(1)}
            ></Pagination.First>
            <Pagination.Prev
              disabled={pageInfo.leftPageNumber <= 1}
              onClick={() =>
                handlePageNumberClick(pageInfo.leftPageNumber - 10)
              }
            ></Pagination.Prev>
            {pageNumbers.map((pageNumber) => (
              <Pagination.Item
                key={pageNumber}
                onClick={() => handlePageNumberClick(pageNumber)}
                active={pageInfo.currentPageNumber === pageNumber}
              >
                {pageNumber}
              </Pagination.Item>
            ))}
            <Pagination.Next
              disabled={pageInfo.rightPageNumber >= pageInfo.totalPages}
              onClick={() =>
                handlePageNumberClick(pageInfo.rightPageNumber + 1)
              }
            ></Pagination.Next>
            <Pagination.Last
              disabled={pageInfo.currentPageNumber === pageInfo.totalPages}
              onClick={() => handlePageNumberClick(pageInfo.totalPages)}
            ></Pagination.Last>
          </Pagination>
        </Col>
      </Row>
    </>
  );
}
