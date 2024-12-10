import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Offcanvas from "react-bootstrap/Offcanvas";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import logo from "../image/logo.png";
import userimage from "../image/user.png";
import "./navbar.css";

function NavBar() {
  const [user, setUser] = useState(null); // 유저 상태 관리
  const navigate = useNavigate();

  // 서버에서 유저 정보를 가져오는 함수
  const fetchUserInfo = async () => {
    const userEmail = localStorage.getItem("user"); // 로컬 스토리지에서 유저 이메일 가져오기

    if (userEmail) {
      try {
        // 유저 이메일을 백엔드로 보내기
        const response = await axios.post("http://localhost:8080/api/user/getUserInfo", {
          user_email: userEmail,
        });
        console.log("유저 받은 데이터확인:",response.data);
        const userData = response.data;

        // 서버에서 받은 유저 정보를 로컬스토리지에 JSON 형태로 저장
        const updatedUser = {
          userEmail: userEmail,
          nickname: userData.nickname,
          user_num: userData.user_num,
        };

        setUser(updatedUser); // 상태 업데이트
        console.log("유저상태 확인:",updatedUser);
        localStorage.setItem("user", JSON.stringify(updatedUser)); // 로컬스토리지에 유저 정보 저장
      } catch (error) {
        console.error("유저 정보를 가져오는 데 실패했습니다.", error);
      }
    }
  };

  // 컴포넌트가 마운트될 때 로컬스토리지에서 유저 정보 확인
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    console.log("세이브유저 확인:", savedUser);
    fetchUserInfo();
    const usertest = localStorage.getItem("user");
    console.log("세이브유저 확인:", usertest);
  }, []); // 처음 마운트될 때 한 번만 호출

  const handleLogout = () => {
    // 로그아웃 처리
    axios
      .post("http://localhost:8080/api/logout", {}, { withCredentials: true })
      .then(() => {
        setUser(null); // 상태 초기화
        localStorage.removeItem("user"); // 로컬스토리지에서 유저 정보 삭제
        window.location.reload(); // 페이지 리로드
      })
      .catch((error) => {
        console.error("로그아웃 중 오류가 발생했습니다.", error);
      });
  };

  const handleNavigateAndReload = (path) => {
    navigate(path); // 페이지 이동
  };

  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg" className="navbar-custom sticky-top shadow-sm">
        <Container>
          {/* 로고 */}
          <Navbar.Brand href="/Allboard" className="d-flex align-items-center">
            <img
              src={logo}
              alt="logo"
              className="d-inline-block align-top me-2"
              style={{ height: "40px" }}
            />
            <span className="navbar-brand-text">MZ Recipe</span>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="offcanvasNavbar" />
          <Navbar.Offcanvas
            id="offcanvasNavbar"
            aria-labelledby="offcanvasNavbarLabel"
            placement="end"
          >
            <Offcanvas.Header closeButton>
              <Offcanvas.Title id="offcanvasNavbarLabel">메뉴</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
              <Nav className="justify-content-start flex-grow-1 pe-3">
                <Nav.Link
                  onClick={() => handleNavigateAndReload("/Allboard")}
                  className="text-light"
                >
                  전체 레시피
                </Nav.Link>
                <Nav.Link
                  onClick={() => handleNavigateAndReload("/Bestboard")}
                  className="text-light"
                >
                  베스트 레시피
                </Nav.Link>
                <Nav.Link
                  onClick={() => handleNavigateAndReload("/Honorboard")}
                  className="text-light"
                >
                  명예의 전당
                </Nav.Link>
                <Nav.Link
                  onClick={() => handleNavigateAndReload("/inquiry")}
                  className="text-light"
                >
                  문의
                </Nav.Link>
              </Nav>
              <Nav className="justify-content-end flex-grow-1 pe-3">
                {user ? (
                  <>
                    {/* 유저 프로필 이미지 */}
                    <img
                      src={userimage} // 프로필 이미지 URL을 서버에서 가져온 값으로 설정
                      alt="User"
                      className="rounded-circle me-2"
                      style={{
                        height: "30px",
                        width: "30px",
                        objectFit: "cover",
                      }}
                    />
                    {/* 유저 이메일이나 닉네임 표시 */}
                    <span className="text-light me-2">{user.nickname}</span> {/* 이메일 또는 닉네임을 표시 */}

                    <Button
                      variant="outline-danger"
                      onClick={handleLogout}
                      className="me-2"
                    >
                      로그아웃
                    </Button>
                    <Button variant="outline-primary" href="/mypage">
                      마이페이지
                    </Button>
                  </>
                ) : (
                  <>
                    <Button
                      variant="outline-primary"
                      href="/login"
                      className="me-2"
                    >
                      로그인
                    </Button>
                    <Button variant="outline-primary" href="/signup">
                      회원가입
                    </Button>
                  </>
                )}
              </Nav>
            </Offcanvas.Body>
          </Navbar.Offcanvas>
        </Container>
      </Navbar>
    </>
  );
}

export default NavBar;
