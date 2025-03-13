import { BrowserRouter, Route, Routes } from "react-router-dom"
import LoginPage from "./pages/LoginPage"
import ToDoPage from "./pages/ToDoPage"
import { Box } from "@chakra-ui/react"
//import Header from "./components/Header"


function Router() {
  return (
    <Box width={"100vw"} height={"100vh"} bg={"whiteAlpha.200"}>
      {/*<Header/>*/}
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<ToDoPage/>}/>
          <Route path="/login" element={<LoginPage/>} />
        </Routes>
      </BrowserRouter>
    </Box>
  )
}

export default Router
