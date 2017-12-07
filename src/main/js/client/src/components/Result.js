import * as React from 'react'
import Paper from 'material-ui/Paper'
import Subheader from 'material-ui/Subheader'

export const Result = ({score, p: {url}}) => (
  <Paper
    zDepth={3}
    style={{
      height: 256,
      width: 256,
      margin: 10,
      wordBreak: 'break-all'
    }}
  >
    <a style={{textDecoration: 'none'}}target="_blank" href={`http://www.wikipedia.com/${url}`}>
      <Subheader style={{color: '#1a237e'}}>{url}</Subheader>
    </a>
    <Subheader>score: {score}</Subheader>
  </Paper>
)
