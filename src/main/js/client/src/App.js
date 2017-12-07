import React from 'react'
import {compose, withState, withHandlers} from 'recompose'
import {Result} from './components/Result'
import RaisedButton from 'material-ui/RaisedButton'
import TextField from 'material-ui/TextField'
import CircularProgress from 'material-ui/CircularProgress'

const enhance = compose(
  withState('query', 'setQuery', ''),
  withState('isSearching', 'setIsSearching', false),
  withState('results', 'setResults', []),
  withHandlers({
    search: ({
      query,
      setResults,
      isSearching,
      setIsSearching,
      results,
    }) => () => {
      if (results.length) setResults([])
      if (!isSearching) setIsSearching(true)

      // using native chrome fetch
      fetch(`http://localhost:8080/search/${query}`, {
        headers: {
          'Access-Control-Allow-Origin': '*',
        },
      })
        .then(res => res.json())
        .then(searchResult => {
          setIsSearching(false)
          setResults([...searchResult.slice(0, 5)])
        })
        .catch(error => console.log(error))
    },
  })
)

export const App = enhance(
  ({results, setQuery, query, search, isSearching}) => (
    <div className="main-container">
      <div
        style={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          flexDirection: 'column',
        }}
      >
        <TextField
          value={query}
          hintText={'What do you want to search for?'}
          onChange={e => setQuery(e.target.value)}
        />
        <RaisedButton
          primary
          label={'Search'}
          onClick={() => search()}
          disabled={isSearching || !query}
          style={{marginTop: 12}}
        />
      </div>
      <div
        style={{
          display: 'flex',
          flexDirection: 'row',
          marginTop: 32
        }}
      >
        {isSearching && (
          <div
            style={{
              display: 'flex',
              height: 512,
              width: '100%',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            <CircularProgress thickness={4} />
          </div>
        )}
        {results.map((result, i) => <Result key={i} {...result} />)}
      </div>
    </div>
  )
)
