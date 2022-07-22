import { 
    Button,
    Grid, 
    TextField, 
    Typography,
    Link,
    Container, 
} from "@material-ui/core";
import React from "react";
import { signup } from "./service/ApiService";

class Signup extends React.Component {
    constructor(props) {
        super(props);
        this.hadleSubmit = this.hadleSubmit.bind(this);
    }

    hadleSubmit(event) {
        event.preventDefault();
        // 오브젝트에서 form에 저장된 데이터를 맵의 형태로 변환
        const data = new FormData(event.target);
        const username = data.get('username');
        const email = data.get('email');
        const password = data.get('password');
        signup({ email: email, username: username, password: password }).then(
            (response) => {
                // 계정 생성 성공 시 login 페이지로 리디렉트
                window.location.href = "/login";
            }
        );
    }

    render() {
        return (
            <Container component="main" maxWidth="xs" style={{ marginTop: "8%" }}>
                <form noValidate onSubmit={this.handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} >
                            <Typography component="h1" variant="h5">
                                create account
                            </Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                autoComplete="fnamw"
                                name="username"
                                variant="outlined"
                                required
                                fullWidth
                                id="username"
                                label="사용자 이름"
                                autoFocus
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="email"
                                label="이메일 주소"
                                name="email"
                                autoComplete="email"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                id="password"
                                label="비밀번호"
                                name="password"
                                autoComplete="current-password"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                계정 생성
                            </Button>
                        </Grid>
                    </Grid>
                    <Grid style={{ justifyContent: "flex-end" }} container>
                        <Grid item>
                            <Link href="/login" variant="body2">
                                If you have an account , you can login on login page.
                            </Link>
                        </Grid>
                    </Grid>

                </form>
            </Container>
        );
    }
}

export default Signup;