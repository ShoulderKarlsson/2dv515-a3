import React from 'react'
import {compose, withState, withHandlers} from 'recompose'
import {Result} from './components/Result'
// import fetch from 'isomorphic-fetch'

const enhance = compose(
  withState('query', 'setQuery', ''),
  withState('results', 'setResults', []),
  withHandlers({
    search: ({query, setResults}) => () => {
      if (!query) {
        return
      }

      fetch(`http://localhost:8080/search/${query}`, {
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      })
        .then(res => res.json())
        .then(searchResult => {
          setResults([...searchResult.slice(0, 5)])
        })
        .catch(error => console.log(error))
    },
  })
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
        {results.length
          ? results.map((result, i) => <Result {...result} key={i} />)
          : null}
      </div>
    </div>
  )
})
