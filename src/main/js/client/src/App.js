import React from 'react'
import {compose, withState, withHandlers} from 'recompose'
import {Result} from './components/Result'
import fetch from 'isomorphic-fetch'

const enhance = compose(
  withState('query', 'setQuery', ''),
  withState('results', 'setResults', []),
  withHandlers({
    search: ({query, setResults}) => () => {
      if (!query) {
        return
      }

      fetch(`http://localhost:8080/search/${query}`, {
        method: 'GET',
        mode: 'cors',
        headers: {
          Accept: 'application/json',
          // 'Access-Control-Allow-Origin': '*',
        },
      })
        .then(response => response.json())
        .then(result => {
          console.log(result)
        })
        .catch(error => console.log(error))
    },
  }),
)

export const App = enhance(({results, setQuery, search, ...props}) => {
  console.log(props)
  return (
    <div className="main-container">
      <div className="input-container">
        <input type="text" onChange={e => setQuery(e.target.value)} />
        <input type="button" onClick={() => search()} value="Search" />
      </div>

      <div className="bottom-section">
        {results.lenght
          ? results.map((result, i) => <Result {...result} key={i} />)
          : null}
      </div>
    </div>
  )
})
