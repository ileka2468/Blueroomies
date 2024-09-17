import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Roomate Finding App</h1>

      <p className="read-the-docs">
        Repo: <a href="https://github.com/ileka2468/se452-group-project">https://github.com/ileka2468/se452-group-project</a> 
      </p>
    </>
  )
}

export default App
