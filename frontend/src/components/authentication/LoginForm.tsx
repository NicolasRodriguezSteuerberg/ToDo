
import { useState } from "react"
import { Button, Flex, Heading, Text, VStack } from "@chakra-ui/react"
import FormInput from "./FormInput"
import TogglePromp from "../../utils/props/AuthenticationPromp"


const LoginForm:React.FC<TogglePromp> = ({toggleForm}) => {
    // manejadores de las pestanhas
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        console.log("login")
    }

    return(
        <VStack
            width={"400px"}
            spacing={"4"}
            p={10}
        >
            <Heading>Login</Heading>
            <form onSubmit={handleSubmit}>
                <VStack spacing="4">
                    <FormInput
                        id="username"
                        label="Username or Email"
                        type="text"
                        placeholder="Nico | nico@gmail.com"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                    <FormInput
                        id="password"
                        label="Password"
                        type="password"
                        placeholder="1A#4sq8"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                    <Button width={"full"} type="submit">Login</Button>
                </VStack>
            </form>

            <Flex align={"center"} justify={"center"} gap={2}>
                <Text fontSize={"14px"}>Don't have an account?</Text>
                <Text 
                    fontSize={"16px"} fontWeight={"bold"}
                    cursor={"pointer"} onClick={toggleForm}
                >Register</Text>
            </Flex>
        </VStack>
    )
}

export default LoginForm