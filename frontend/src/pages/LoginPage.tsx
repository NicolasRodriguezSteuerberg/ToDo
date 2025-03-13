
import { useState } from "react"
import { Box, Flex } from "@chakra-ui/react"
import LoginForm from "../components/authentication/LoginForm"
import RegisterForm from "../components/authentication/RegisterForm"


const LoginPage = () => {
    // manejadores de las pestanhas
    const [isInLogging, setLogin] = useState(true)

    const toggleForm = () => {
        console.log("change form")
        setLogin(!isInLogging)
    }

    return(
        <Flex
            width={"100%"}
            height={"100%"}
            justify={"center"} 
            align={"center"}
        >
            <Box boxShadow="md">
                {isInLogging?
                    <LoginForm toggleForm={toggleForm}/> 
                : 
                    <RegisterForm toggleForm={toggleForm}/>
                }
            </Box>
        </Flex>
    )
}

export default LoginPage