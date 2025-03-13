import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import Router from './Router'
import { ChakraProvider } from '@chakra-ui/react'

createRoot(document.getElementById('root')!).render(

  <StrictMode>
    <ChakraProvider>
        <Router/>
    </ChakraProvider>
  </StrictMode>,
)
