
import { useState } from "react"
import { Button, Flex, Heading, Text, VStack } from "@chakra-ui/react"
import FormInput from "./FormInput"
import TogglePromp from "../../utils/props/AuthenticationPromp"


const RegisterForm:React.FC<TogglePromp> = ({toggleForm}) => {
    // manejadores de las pestanhas
    const [username, setUsername] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")


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
            <Heading>Register</Heading>
            <form onSubmit={handleSubmit}>
                <VStack spacing="4">
                    <FormInput
                        id="username"
                        label="Username"
                        type="text"
                        placeholder="Nico"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                    <FormInput
                        id="email"
                        label="Email"
                        type="email"
                        placeholder="nico@gmail.com"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                    />
                    <FormInput
                        id="password"
                        label="Password"
                        type="password"
                        placeholder="1A#4sq8"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                    <FormInput
                        id="confirm_password"
                        label="Confirm Password"
                        type="password"
                        placeholder="1A#4sq8"
                        value={confirmPassword}
                        onChange={e => setConfirmPassword(e.target.value)}
                    />
                    <Button width={"full"} type="submit">Login</Button>
                </VStack>
            </form>

            <Flex align={"center"} justify={"center"} gap={2}>
                <Text fontSize={"14px"}>Do you have an account?</Text>
                <Text 
                    fontSize={"16px"} fontWeight={"bold"}
                    cursor={"pointer"} onClick={toggleForm}
                >Login</Text>
            </Flex>
        </VStack>
    )
}

export default RegisterForm